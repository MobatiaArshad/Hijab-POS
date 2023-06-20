package com.a71cities.hijab.ppm.ui.paymentType

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.api.repositories.HijabApiRepository
import com.a71cities.hijab.ppm.database.model.SaleEntity
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.extras.Constants.CART_DATA
import com.a71cities.hijab.ppm.extras.SingleLiveEvent
import com.a71cities.hijab.ppm.extras.getErrorResponse
import com.a71cities.hijab.ppm.ui.createBill.model.CartDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PaymentTypeBottomSheetViewModel @Inject constructor(
    private val dbRepository: HijabRoomRepository,
    private val apiRepository: HijabApiRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val passedCardData = MutableLiveData<CartDataClass>(savedStateHandle[CART_DATA])
    val loader: MutableLiveData<Boolean> = MutableLiveData()
    val dataSubmitted = SingleLiveEvent<Boolean>()

    fun addNewBill() = viewModelScope.launch {
            try {
                loader.value = true
                apiRepository.addSales(passedCardData.value!!)
                loader.value = false
                dataSubmitted.value = true
            }catch (e: HttpException) {
                loader.value = false
                e.printStackTrace()
                e.getErrorResponse()
            }
//        dbRepository.newBill(passedCardData.value!!)
    }
}