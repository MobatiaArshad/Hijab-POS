package com.a71cities.hijab.ppm.ui.products.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductsResponse(
    @SerializedName("data")
    var `data`: List<Data>? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
): Parcelable {

    @Parcelize
    data class Data(
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("productCode")
        var productCode: Int? = null,
        @SerializedName("img")
        var img: String? = null,
        @SerializedName("price")
        var price: String? = null,
        @SerializedName("productName")
        var productName: String? = null,
        @SerializedName("productTypeId")
        var productTypeId: String? = null,
        @SerializedName("productTypeName")
        var productTypeName: String? = null,
        @SerializedName("salePrice")
        var salePrice: String? = null,
        @SerializedName("size")
        var size: String? = null,
        @SerializedName("quantity")
        var quantity: String? = null,
        var cartQty: Int = 1
    ): Parcelable
}