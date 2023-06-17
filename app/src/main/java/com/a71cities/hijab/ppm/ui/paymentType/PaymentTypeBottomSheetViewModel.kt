package com.a71cities.hijab.ppm.ui.paymentType

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a71cities.hijab.ppm.database.model.SaleEntity
import com.a71cities.hijab.ppm.database.repository.HijabRoomRepository
import com.a71cities.hijab.ppm.extras.Constants.CART_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentTypeBottomSheetViewModel @Inject constructor(
    private val dbRepository: HijabRoomRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val passedCardData = MutableLiveData<SaleEntity>(savedStateHandle[CART_DATA])

    fun addNewBill() = viewModelScope.launch {
        dbRepository.newBill(passedCardData.value!!)
    }
}