package com.a71cities.hijab.ppm.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.a71cities.hijab.ppm.database.listConverters.ProductListConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Sales")
data class SaleEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,

    @TypeConverters(ProductListConverters::class)
    var soldItems: ArrayList<ProductsEntity>? =null,

    var phoneNumber: String? = null,
    var paymentType: Int? = 1,
    var subTotal: Int? = null,
    var paidAmount: Int? = null,
    var date: String? = null
): Parcelable
