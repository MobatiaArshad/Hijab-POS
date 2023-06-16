package com.a71cities.hijab.ppm.ui.addProductType

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.a71cities.hijab.ppm.R
import com.a71cities.hijab.ppm.application.HijabApplication
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.databinding.FragmentAddProductTypeBinding
import com.a71cities.hijab.ppm.extras.log
import com.a71cities.hijab.ppm.extras.toJson
import com.a71cities.hijab.ppm.ui.addProductType.adapter.ProductTypeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class AddProductTypeFragment : Fragment() {

    companion object {
        fun newInstance() = AddProductTypeFragment()
    }

    private lateinit var viewModel: AddProductTypeViewModel
    private lateinit var binding: FragmentAddProductTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductTypeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AddProductTypeViewModel::class.java]

        observers()

        binding.submitBtn.setOnClickListener {
            addType()
            binding.proTypeEdt.text?.clear()
        }
    }

    private fun observers() {
        viewModel.types.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = ProductTypeAdapter(it)
        }
    }

    private fun addType() {
        val type = ProductTypeEntity(productType = binding.proTypeEdt.text?.trim().toString())

        viewModel.insertType(type)
    }

}