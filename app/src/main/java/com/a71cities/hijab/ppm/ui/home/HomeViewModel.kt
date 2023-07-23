package com.a71cities.hijab.ppm.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.api.repositories.HijabApiRepository
import com.a71cities.hijab.ppm.base.BaseViewModel
import com.a71cities.hijab.ppm.database.model.SaleEntity
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.extras.getErrorResponse
import com.a71cities.hijab.ppm.extras.getTodayDate
import com.a71cities.hijab.ppm.ui.home.model.HomeData
import com.a71cities.hijab.ppm.ui.home.model.HomeDataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dbRepository: HijabRoomRepository,
    private val apiRepository: HijabApiRepository
) : BaseViewModel() {

    val saleResponse = MutableLiveData<List<HomeDataResponse.Data>>()

    init {
        getSaleToday()
    }

    private fun getSaleToday() {
        viewModelScope.launch {
            try {
                loader.value = true
                saleResponse.value = apiRepository.getSaleToday(getTodayDate()).data!!
                loader.value = false
            }catch (e: HttpException) {
                loader.value = false
                e.printStackTrace()
                e.getErrorResponse()
            }
        }
    }

}