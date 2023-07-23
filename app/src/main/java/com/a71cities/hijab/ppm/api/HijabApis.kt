package com.a71cities.hijab.ppm.api

import com.a71cities.hijab.ppm.api.model.CommonResponse
import com.a71cities.hijab.ppm.ui.addProductType.model.ProductTypeResponse
import com.a71cities.hijab.ppm.ui.createBill.model.CartDataClass
import com.a71cities.hijab.ppm.ui.home.model.HomeDataResponse
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HijabApis {

    @POST("addProductType")
    suspend fun addProductType(
        @Body hashMap: HashMap<String,String>
    ): CommonResponse

    @GET("productTypes")
    suspend fun getProductsTypes(): ProductTypeResponse

    @POST("addProducts")
    suspend fun addProducts(
        @Body hashMap: HashMap<String,String>
    ): CommonResponse

    @GET("productsByType")
    suspend fun getProductsByTypeId(
        @Query("productTypeId") id: String
    ): ProductsResponse

    @GET("searchProductCode")
    suspend fun searchProductCode(
        @Query("productCode") id: String
    ): ProductsResponse

    @POST("addSales")
    suspend fun addSales(
        @Body cartData: CartDataClass
    ): CommonResponse

    @GET("getSaleToday")
    suspend fun getSaleToday(
        @Query("date") date: String
    ): HomeDataResponse

    @PUT("updateProduct/{id}")
    suspend fun updateProduct(
        @Body map: HashMap<String, String>,
        @Path("id") id: Int
    ): CommonResponse
}