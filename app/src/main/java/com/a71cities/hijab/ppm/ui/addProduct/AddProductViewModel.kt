package com.a71cities.hijab.ppm.ui.addProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.base.BaseViewModel
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.api.repositories.HijabApiRepository
import com.a71cities.hijab.ppm.extras.Constants.PASSED_PRODUCT
import com.a71cities.hijab.ppm.extras.SingleLiveEvent
import com.a71cities.hijab.ppm.extras.getErrorResponse
import com.a71cities.hijab.ppm.ui.addProductType.model.ProductTypeResponse
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val repository: HijabRoomRepository,
    private val apiRepository: HijabApiRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {


    val passedEditData = MutableLiveData<ProductsResponse.Data?>(savedStateHandle[PASSED_PRODUCT])
    val typesResponse = MutableLiveData<List<ProductTypeResponse.Data>>()
    val dataSubmitted = SingleLiveEvent<Boolean>()
    val imgToUpload = MutableLiveData<String>()

    init {
        getTypes()
    }

    private fun getTypes() {
        viewModelScope.launch {
            try {
                loader.value = true
                typesResponse.value = apiRepository.getProductType().data!!
                loader.value = false
            } catch (e: HttpException) {
                e.printStackTrace()
                loader.value = false
                showAlertTxt.value = e.getErrorResponse()
            }

        }
    }

    fun addProduct(rawData: HashMap<String,String>) = viewModelScope.launch {
        try {
            dataSubmitted.value = apiRepository.addProduct(rawData).status!!
        } catch (e: HttpException) {
            e.printStackTrace()
            loader.value = false
            showAlertTxt.value = e.getErrorResponse()
        }
    }

    fun update(rawData: HashMap<String, String>,id: Int) = viewModelScope.launch {
        try {
            dataSubmitted.value = apiRepository.updateProduct(rawData,id).status!!
        } catch (e: HttpException) {
            e.printStackTrace()
            loader.value = false
            showAlertTxt.value = e.getErrorResponse()
        }
    }
}