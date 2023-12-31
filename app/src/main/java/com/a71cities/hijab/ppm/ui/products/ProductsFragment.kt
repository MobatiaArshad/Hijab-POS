package com.a71cities.hijab.ppm.ui.products

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.databinding.FragmentProductsBinding
import com.a71cities.hijab.ppm.extras.Constants.PASSED_PRODUCT
import com.a71cities.hijab.ppm.extras.clippingBottomRec
import com.a71cities.hijab.ppm.extras.clippingRec
import com.a71cities.hijab.ppm.ui.addProductType.model.ProductTypeResponse
import com.a71cities.hijab.ppm.ui.products.adapter.ProductAdapter
import com.a71cities.hijab.ppm.ui.products.adapter.ProductCategoryAdapter
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private var proTypeId: String? = null
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
        viewModel.typesResponse.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.categoryRec.apply {
                    clippingRec(it.size)

                    adapter = ProductCategoryAdapter(it) { entity ->
                        proTypeId = entity.id.toString()
                        viewModel.getProductsByType(proTypeId!!)
                    }
                }
            }
        }

        viewModel.productsData.observe(viewLifecycleOwner) {
            binding.productsRec.apply {
                clippingBottomRec(70)

                adapter = ProductAdapter(it) {
                    findNavController().navigate(R.id.action_edit_productsFragment_to_addProductFragment,
                        bundleOf(
                            PASSED_PRODUCT to it
                        )
                    )
                }
            }
        }

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.addProBtn.id -> findNavController().navigate(R.id.action_productsFragment_to_addProductFragment)
        }
    }
}