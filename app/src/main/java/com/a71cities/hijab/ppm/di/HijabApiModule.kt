package com.a71cities.hijab.ppm.di

import com.a71cities.hijab.ppm.api.HijabApis
import com.a71cities.hijab.ppm.extras.Constants.IMG_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HijabApiModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(IMG_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): HijabApis = retrofit.create(HijabApis::class.java)
}