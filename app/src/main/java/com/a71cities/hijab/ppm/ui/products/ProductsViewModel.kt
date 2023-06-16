package com.a71cities.hijab.ppm.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: HijabRoomRepository,
) : ViewModel() {


    val types = MutableLiveData<List<ProductTypeEntity>>()
    val productsData = MutableLiveData<List<ProductsEntity>>()

    init {
        getTypes()
    }

    fun getProductsByType(id: Int) {
        viewModelScope.launch {
            productsData.value = repository.getProductsByType(id)
        }
    }

    private fun getTypes() {
        viewModelScope.launch {
            types.value = repository.getTypes()
        }
    }




}