package com.a71cities.hijab.ppm.ui.products

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.databinding.FragmentProductsBinding
import com.a71cities.hijab.ppm.extras.clippingBottomRec
import com.a71cities.hijab.ppm.extras.clippingRec
import com.a71cities.hijab.ppm.ui.products.adapter.ProductAdapter
import com.a71cities.hijab.ppm.ui.products.adapter.ProductCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private var proTypeId: Int? = 1
    private lateinit var viewModel: ProductsViewModel
    private lateinit var binding: FragmentProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ProductsViewModel::class.java]

        observers()

        binding.addProBtn.setOnClickListener(this)
    }

    private fun observers() {
        viewModel.types.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                proTypeId = it[0].id
                binding.categoryRec.apply {
                    clippingRec(it.size)
                    adapter = ProductCategoryAdapter(it) { entity ->
                        proTypeId = entity.id
                        viewModel.getProductsByType(proTypeId!!)
                    }
                }
            }
        }

        viewModel.productsData.observe(viewLifecycleOwner) {
            binding.productsRec.apply {
                clippingBottomRec(70)
                adapter = ProductAdapter(it)
            }
        }

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.addProBtn.id -> findNavController().navigate(R.id.action_productsFragment_to_addProductFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductsByType(proTypeId!!)
    }
}