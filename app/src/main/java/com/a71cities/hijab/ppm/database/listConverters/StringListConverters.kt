package com.a71cities.hijab.ppm.database.listConverters

import androidx.room.TypeConverter
import com.google.gson.Gson


class StringListConverters {

    @TypeConverter
    fun listToJsonString(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

}