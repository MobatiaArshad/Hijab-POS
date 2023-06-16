package com.a71cities.hijab.ppm.ui.addProductType

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductTypeViewModel @Inject constructor(
    private val repository: HijabRoomRepository
) : ViewModel() {

    val types = MutableLiveData<List<ProductTypeEntity>>()

    init {
        getTypes()
    }

    private fun getTypes() {
        viewModelScope.launch {
            types.value = repository.getTypes()
        }
    }

    fun insertType(type: ProductTypeEntity) = viewModelScope.launch {
        repository.addProductType(type)

        getTypes()
    }
}