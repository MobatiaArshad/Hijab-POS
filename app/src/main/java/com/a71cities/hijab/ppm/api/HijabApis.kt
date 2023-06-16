package com.a71cities.hijab.ppm.api

import retrofit2.http.GET
import retrofit2.http.POST

interface HijabApis {

    @POST("")
    suspend fun uploadImage()

    @GET("")
    suspend fun getImage()
}