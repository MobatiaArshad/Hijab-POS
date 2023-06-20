package com.a71cities.hijab.ppm.ui.createBill.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartDataClass(
    var soldItems: List<String>? =null,
    var phoneNumber: String? = null,
    var paymentType: Int? = 1,
    var subTotal: Int? = null,
    var paidAmount: Int? = null,
    var date: String? = null,
    var shop: String? = "1"
): Parcelable
