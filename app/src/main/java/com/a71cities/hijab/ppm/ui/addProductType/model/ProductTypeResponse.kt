package com.a71cities.hijab.ppm.ui.addProductType.model


import com.google.gson.annotations.SerializedName

data class ProductTypeResponse(
    @SerializedName("data")
    var `data`: List<Data>? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
) {
    data class Data(
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("productType")
        var productType: String? = null
    )
}