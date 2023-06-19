package com.a71cities.hijab.ppm.ui.addProductType

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.api.repositories.HijabApiRepository
import com.a71cities.hijab.ppm.base.BaseViewModel
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.extras.getErrorResponse
import com.a71cities.hijab.ppm.ui.addProductType.model.ProductTypeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AddProductTypeViewModel @Inject constructor(
    private val repository: HijabRoomRepository,
    private val apiRepository: HijabApiRepository
) : BaseViewModel() {

    val typesResponse = MutableLiveData<List<ProductTypeResponse.Data>>()

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

    fun insertType(map: HashMap<String,String>) = viewModelScope.launch {

        try {
            apiRepository.addProductType(map)
        }catch (e: HttpException) {
            e.printStackTrace()
            loader.value = false
            showAlertTxt.value = e.getErrorResponse()
        }
        getTypes()
    }
}