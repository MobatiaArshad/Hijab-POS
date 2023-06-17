package com.a71cities.hijab.ppm.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.database.model.SaleEntity
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.extras.getTodayDate
import com.a71cities.hijab.ppm.ui.home.model.HomeData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dbRepository: HijabRoomRepository
) : ViewModel() {

    val saleResponse = MutableLiveData<List<SaleEntity>>()

    init {
        getSaleToday()
    }

    private fun getSaleToday() {
        viewModelScope.launch {
            saleResponse.value = dbRepository.getSaleToday("%${getTodayDate()}%")
        }
    }

}