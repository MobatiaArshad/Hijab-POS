package com.a71cities.hijab.ppm.api.model


import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("data")
    var `data`: String? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: Boolean? = null
)