package com.a71cities.hijab.ppm.ui.home.model


import com.google.gson.annotations.SerializedName

data class HomeDataResponse(
    @SerializedName("data")
    var `data`: List<Data>? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
) {
    data class Data(
        @SerializedName("date")
        var date: String? = null,
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("paidAmount")
        var paidAmount: String? = null,
        @SerializedName("paymentType")
        var paymentType: Int? = null,
        @SerializedName("shop")
        var shop: Int? = null,
        @SerializedName("soldItems")
        var soldItems: String? = null
    )
}