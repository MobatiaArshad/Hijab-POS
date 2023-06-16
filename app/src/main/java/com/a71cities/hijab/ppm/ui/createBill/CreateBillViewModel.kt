package com.a71cities.hijab.ppm.ui.createBill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBillViewModel @Inject constructor(
    private val dbRepository: HijabRoomRepository
) : ViewModel() {

    val productsData = MutableLiveData<List<ProductsEntity>>()

    fun searchProCode(code: String) {
        viewModelScope.launch {
            productsData.value = dbRepository.getProductByCode(code)
        }
    }

    val bills = arrayListOf(
        3300,
        1200,
        30,
        110,
    )
}