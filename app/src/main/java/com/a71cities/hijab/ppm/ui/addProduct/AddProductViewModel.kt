package com.a71cities.hijab.ppm.ui.addProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.base.BaseViewModel
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.api.repositories.HijabApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: HijabRoomRepository,
    private val apiRepository: HijabApiRepository
) : BaseViewModel() {


    val types = MutableLiveData<List<ProductTypeEntity>>()
    val imgToUpload = MutableLiveData<String>()

    init {
        getTypes()
    }

    private fun getTypes() {
        viewModelScope.launch {
            types.value = repository.getTypes()
        }
    }

    fun uploadImg() {

    }

    fun addProduct(products: ProductsEntity) = viewModelScope.launch {
        repository.addProduct(products)
    }
}