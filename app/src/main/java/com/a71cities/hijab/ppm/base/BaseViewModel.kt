package com.a71cities.hijab.ppm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a71cities.hijab.ppm.api.repositories.HijabApiRepository
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.extras.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {


    val showAlertTxt = SingleLiveEvent<String>()
    val showAlertRes = SingleLiveEvent<Int>()
    val loader: MutableLiveData<Boolean> = MutableLiveData()
    val showSkeleton: MutableLiveData<Boolean> = MutableLiveData()
    var notificationCount= MutableLiveData<String>()
    val msgCount = MutableLiveData<String>()


}