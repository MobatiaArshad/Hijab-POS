package com.a71cities.hijab.ppm.di

import android.content.Context
import androidx.room.Room
import com.a71cities.hijab.ppm.database.HijabRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HijabRoomModule {

    private var INSTANCE: HijabRoomDatabase? = null

    @Singleton
    @Provides
    fun getDatabase(
        @ApplicationContext context: Context
    ): HijabRoomDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(context, HijabRoomDatabase::class.java, "Hijab")
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }

    @Singleton
    @Provides
    fun hijabDao(db: HijabRoomDatabase) = db.hijabDoa()
}