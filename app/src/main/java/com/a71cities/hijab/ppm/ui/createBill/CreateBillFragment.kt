package com.a71cities.hijab.ppm.ui.createBill

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.databinding.FragmentCreateBillBinding
import com.a71cities.hijab.ppm.ui.createBill.adapter.CreateBillAdapter
import com.a71cities.hijab.ppm.extras.clippingBottomRec
import com.a71cities.hijab.ppm.extras.log
import com.a71cities.hijab.ppm.extras.searchQueryTyped
import com.a71cities.hijab.ppm.extras.toJson
import com.a71cities.hijab.ppm.ui.createBill.adapter.ProductCodeSpinnerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class CreateBillFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = CreateBillFragment()
    }

    private lateinit var viewModel: CreateBillViewModel
    private lateinit var binding: FragmentCreateBillBinding
    var cartArray = arrayListOf<ProductsEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateBillBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CreateBillViewModel::class.java]

        setUI()
        observer()

        binding.searchEdt.apply {
            searchQueryTyped { isEmpty, value ->
                if (hasFocus()) {
                    viewModel.searchProCode("%$value%")
                }
            }
        }




        binding.confirmBtn.setOnClickListener(this)
    }

    private fun observer() {
        viewModel.productsData.observe(viewLifecycleOwner) {

            binding.searchEdt.setAdapter(ProductCodeSpinnerAdapter(requireContext(),it))
            binding.searchEdt.showDropDown()

            binding.searchEdt.setOnItemClickListener { adapterView, view, position, l ->
                val item = adapterView.getItemAtPosition(position) as ProductsEntity
                cartArray.add(item)

                binding.recyclerView.adapter?.notifyDataSetChanged()
                setTotalAmount(cartArray.sumOf { m -> m.salePrice })

                binding.searchEdt.text.clear()
            }
        }
    }

    private fun setUI() {


        setTotalAmount(cartArray.sumOf { it.salePrice })

        binding.recyclerView.apply {
            clippingBottomRec(70)
            adapter = CreateBillAdapter(cartArray)
        }

        val itemHolder = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerView.adapter!!
                cartArray.removeAt(viewHolder.absoluteAdapterPosition)
                adapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                adapter.notifyItemRangeChanged(viewHolder.absoluteAdapterPosition,viewModel.bills.size)

                setTotalAmount(cartArray.sumOf { it.salePrice })
            }

        })

        itemHolder.attachToRecyclerView(binding.recyclerView)

    }

    private fun setTotalAmount(input: Int) {
        binding.subTotalTxt.text = "SubTotal: ₹$input/-"
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.confirmBtn.id -> findNavController().navigate(R.id.paymentTypeBottomSheet)
        }
    }
}