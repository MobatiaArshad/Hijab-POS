package com.a71cities.hijab.ppm.database.listConverters

import androidx.room.TypeConverter
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductListConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): ArrayList<ProductsEntity> {
        val type = object : TypeToken<ArrayList<ProductsEntity>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromProductsList(productsList: ArrayList<ProductsEntity>): String {
        return gson.toJson(productsList)
    }
}