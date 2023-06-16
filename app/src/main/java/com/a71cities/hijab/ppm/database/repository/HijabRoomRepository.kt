package com.a71cities.hijab.ppm.database.repository

import androidx.annotation.WorkerThread
import com.a71cities.hijab.ppm.database.HijabDao
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import javax.inject.Inject

class HijabRoomRepository @Inject constructor(
    private val dao: HijabDao
) {


    @WorkerThread
    suspend fun addProductType(type: ProductTypeEntity) {
        dao.insertProductType(type)
    }

    @WorkerThread
    suspend fun getTypes(): List<ProductTypeEntity> = dao.getProductTypes()

    @WorkerThread
    suspend fun addProduct(products: ProductsEntity) {
        dao.addProduct(products)
    }

    @WorkerThread
    suspend fun getProducts(): List<ProductsEntity> = dao.getProducts()

    @WorkerThread
    suspend fun getProductsByType(id: Int): List<ProductsEntity> = dao.getProductsByType(id)

    @WorkerThread
    suspend fun getProductByCode(code: String): List<ProductsEntity> = dao.getProductByCode(code)

}