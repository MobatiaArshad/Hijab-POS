package com.a71cities.hijab.ppm.ui.home.model

data class HomeData(
    val code: String,
    val type: String,
    val shop: String,
    val price: String,
    val returned: Boolean = false,
    val paymentType: Int = 0
)
