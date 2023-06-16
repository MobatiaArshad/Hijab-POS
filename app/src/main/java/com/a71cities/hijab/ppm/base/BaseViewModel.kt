package com.a71cities.hijab.ppm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.a71cities.hijab.ppm.extras.SingleLiveEvent

open class BaseViewModel: ViewModel() {


    val showAlertTxt = SingleLiveEvent<String>()
    val showAlertRes = SingleLiveEvent<Int>()
    val loader: MutableLiveData<Boolean> = MutableLiveData()
    val showSkeleton: MutableLiveData<Boolean> = MutableLiveData()
    var notificationCount= MutableLiveData<String>()
    val msgCount = MutableLiveData<String>()


}