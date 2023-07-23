package com.a71cities.hijab.ppm.extras

import com.a71cities.hijab.ppm.BuildConfig

object Constants {

    const val LOG ="HIJAB :-> "
    const val IMG_URL ="https://freeimage.host/api/1/upload/"
    const val CART_DATA ="CART_DATA"
    const val PAYMENT_COMPLETED ="PAYMENT_COMPLETED"
    const val PASSED_PRODUCT ="PASSED_PRODUCT"

    val ENDPOINT = when (BuildConfig.FLAVOR) {
        "dev" -> "dev"
        else -> "exec"
    }

}