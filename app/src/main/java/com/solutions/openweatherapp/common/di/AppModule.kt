package com.solutions.openweatherapp.common.di

import android.content.Context
import co.realpost.android.common.persistance.UPSharedPrefs
import com.solutions.openweatherapp.WeatherApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = arrayOf(
    NetworkModule::class))
class AppModule {

    @Provides
    @Singleton
    fun application(): WeatherApp {
        return WeatherApp()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): UPSharedPrefs {
        return UPSharedPrefs(context)
    }
}
