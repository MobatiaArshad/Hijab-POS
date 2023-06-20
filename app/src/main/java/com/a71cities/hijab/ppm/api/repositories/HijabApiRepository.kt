package com.a71cities.hijab.ppm.api.repositories

import com.a71cities.hijab.ppm.api.HijabApis
import com.a71cities.hijab.ppm.api.model.CommonResponse
import com.a71cities.hijab.ppm.ui.addProductType.model.ProductTypeResponse
import com.a71cities.hijab.ppm.ui.createBill.model.CartDataClass
import com.a71cities.hijab.ppm.ui.products.model.ProductsResponse
import javax.inject.Inject

class HijabApiRepository @Inject constructor(
    private val apis: HijabApis
) {

    suspend fun addProductType(map: HashMap<String,String>) {
        apis.addProductType(hashMap = map)
    }

    suspend fun getProductType() : ProductTypeResponse = apis.getProductsTypes()

    suspend fun addProduct(map: HashMap<String, String>): CommonResponse = apis.addProducts(map)

    suspend fun getProductByTypeId(id: String): ProductsResponse = apis.getProductsByTypeId(id)

    suspend fun searchProductCode(code: String): ProductsResponse = apis.searchProductCode(code)

    suspend fun addSales(cartData: CartDataClass): CommonResponse = apis.addSales(cartData)
}