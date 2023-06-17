package com.a71cities.hijab.ppm.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.database.model.SaleEntity

@Dao
interface HijabDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProductType(type: ProductTypeEntity)

    @Query("SELECT * FROM ProductType")
    suspend fun getProductTypes(): List<ProductTypeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: ProductsEntity)

    @Query("SELECT * FROM Products")
    suspend fun getProducts(): List<ProductsEntity>

    @Query("SELECT * FROM Products WHERE productTypeId = :id")
    suspend fun getProductsByType(id: Int): List<ProductsEntity>

    @Query("SELECT * FROM Products WHERE productCode LIKE :code")
    suspend fun getProductByCode(code: String): List<ProductsEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun newBill(saleEntity: SaleEntity)

    @Query("SELECT * FROM sales WHERE date LIKE :date")
    suspend fun getSalesToday(date: String): List<SaleEntity>
}