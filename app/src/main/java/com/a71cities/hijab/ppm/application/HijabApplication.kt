package com.a71cities.hijab.ppm.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.a71cities.hijab.ppm.utils.Pref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HijabApplication: Application() {

    companion object {
        lateinit var instance: HijabApplication
    }

//    val applicationScope = CoroutineScope(SupervisorJob())
//    val database by lazy { HijabRoomDatabase.getDatabase(this, applicationScope) }
//    val repository by lazy { HijabRepository(database.hijabDoa()) }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Pref.init(this)

    }
}