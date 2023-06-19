package com.a71cities.hijab.ppm.ui.createBill

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.api.repositories.HijabApiRepository
import com.a71cities.hijab.ppm.base.BaseViewModel
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.extras.getErrorResponse
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CreateBillViewModel @Inject constructor(
    private val dbRepository: HijabRoomRepository,
    private val apiRepository: HijabApiRepository
) : BaseViewModel() {

    val productsData = MutableLiveData<List<ProductsResponse.Data>>()

    fun searchProCode(code: String) {
        viewModelScope.launch {

            try {
                loader.value = true
                productsData.value = apiRepository.searchProductCode(code).data!!
                loader.value = false
            }catch (e: HttpException) {
                e.printStackTrace()
                loader.value = false
                showAlertTxt.value = e.getErrorResponse()
            }
        }
    }

}