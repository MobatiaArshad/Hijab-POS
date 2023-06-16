package com.a71cities.hijab.ppm.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Sales")
data class SaleEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val soldItems: ArrayList<String>,
    val phoneNumber: Int,
    val paymentType: Int,
    val paidAmount: Int
)
