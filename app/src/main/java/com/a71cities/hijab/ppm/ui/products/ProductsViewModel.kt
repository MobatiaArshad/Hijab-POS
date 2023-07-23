package com.a71cities.hijab.ppm.ui.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.api.repositories.HijabApiRepository
import com.a71cities.hijab.ppm.base.BaseViewModel
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.extras.getErrorResponse
import com.a71cities.hijab.ppm.ui.addProductType.model.ProductTypeResponse
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: HijabRoomRepository,
    private val apiRepository: HijabApiRepository
) : BaseViewModel() {


    val productsData = MutableLiveData<List<ProductsResponse.Data>>()
    val typesResponse = MutableLiveData<List<ProductTypeResponse.Data>>()

    init {
        getTypes()
    }

    fun getTypes() {
        viewModelScope.launch {
            try {
                showSkeleton.value = true
                typesResponse.value = apiRepository.getProductType().data!!
                getProductsByType(typesResponse.value!![0].id.toString())
            } catch (e: HttpException) {
                e.printStackTrace()
                showSkeleton.value = false
                showAlertTxt.value = e.getErrorResponse()
            }

        }
    }

    fun getProductsByType(proTypeId: String) {
        viewModelScope.launch { 
            try {
                productsData.value = apiRepository.getProductByTypeId(proTypeId).data!!
                showSkeleton.value = false
            } catch (e: HttpException) {
                e.printStackTrace()
                showSkeleton.value = false
                showAlertTxt.value = e.getErrorResponse()
            }
        }
    }


}