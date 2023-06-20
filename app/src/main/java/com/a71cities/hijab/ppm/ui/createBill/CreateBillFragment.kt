package com.a71cities.hijab.ppm.ui.createBill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.base.BaseFragment
import com.a71cities.hijab.ppm.databinding.FragmentCreateBillBinding
import com.a71cities.hijab.ppm.extras.Constants.CART_DATA
import com.a71cities.hijab.ppm.extras.Constants.PAYMENT_COMPLETED
import com.a71cities.hijab.ppm.extras.clippingBottomRec
import com.a71cities.hijab.ppm.extras.searchQueryTyped
import com.a71cities.hijab.ppm.ui.createBill.adapter.CreateBillAdapter
import com.a71cities.hijab.ppm.ui.createBill.adapter.ProductCodeSpinnerAdapter
import com.a71cities.hijab.ppm.ui.createBill.model.CartDataClass
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateBillFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = CreateBillFragment()
    }

    override lateinit var viewModel: CreateBillViewModel
    private lateinit var binding: FragmentCreateBillBinding

    var cartArray = arrayListOf<ProductsResponse.Data>()
    lateinit var saleData: CartDataClass

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

        saleData = CartDataClass()
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
                val item = adapterView.getItemAtPosition(position) as ProductsResponse.Data
                cartArray.add(item)

                binding.recyclerView.adapter?.notifyDataSetChanged()
                setTotalAmount(cartArray.sumOf { m -> m.salePrice!!.toInt() })

                binding.searchEdt.text.clear()
            }
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(PAYMENT_COMPLETED)?.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(R.id.homeFragment)
        }
    }

    private fun setUI() {


        setTotalAmount(cartArray.sumOf { it.salePrice!!.toInt() })

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
                adapter.notifyItemRangeChanged(viewHolder.absoluteAdapterPosition,cartArray.size)

                setTotalAmount(cartArray.sumOf { it.salePrice!!.toInt() })
            }

        })

        itemHolder.attachToRecyclerView(binding.recyclerView)

    }

    private fun setTotalAmount(input: Int) {
        saleData.subTotal = input
        binding.subTotalTxt.text = "SubTotal: ₹$input/-"
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.confirmBtn.id -> {
                saleData.soldItems = cartArray.map { m -> m.id.toString() }

                findNavController().navigate(R.id.paymentTypeBottomSheet, bundleOf(CART_DATA to saleData))
            }
        }
    }
}