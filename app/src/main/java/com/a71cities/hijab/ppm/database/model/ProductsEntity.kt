package com.a71cities.hijab.ppm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Products")
data class ProductsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productName: String,
    val productCode: String,
    val productTypeName: String,
    val productTypeId: Int,
    val size: String,
    val price: Int,
    val salePrice: Int,
    val img: String? = null,
)
