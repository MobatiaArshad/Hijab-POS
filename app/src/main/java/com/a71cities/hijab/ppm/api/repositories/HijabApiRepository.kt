package com.a71cities.hijab.ppm.api.repositories

import com.a71cities.hijab.ppm.api.HijabApis
import javax.inject.Inject

class HijabApiRepository @Inject constructor(
    private val apis: HijabApis
) {

    suspend fun uploadImage() {
        apis.uploadImage()
    }

    suspend fun fetchImage() {
        apis.getImage()
    }
}