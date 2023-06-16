package com.a71cities.hijab.ppm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductType")
data class ProductTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productType: String
)