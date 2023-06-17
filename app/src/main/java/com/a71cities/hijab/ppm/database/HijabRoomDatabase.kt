package com.a71cities.hijab.ppm.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.a71cities.hijab.ppm.database.listConverters.ProductListConverters
import com.a71cities.hijab.ppm.database.model.ProductTypeEntity
import com.a71cities.hijab.ppm.database.model.ProductsEntity
import com.a71cities.hijab.ppm.database.model.SaleEntity
import com.a71cities.hijab.ppm.database.listConverters.StringListConverters

@Database(entities = [ProductsEntity::class, ProductTypeEntity::class, SaleEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverters::class,ProductListConverters::class)
abstract class HijabRoomDatabase : RoomDatabase() {

    abstract fun hijabDoa(): HijabDao

}