package com.solutions.openweatherapp.common.di

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import com.solutions.openweatherapp.model.CitiesDB
import com.solutions.openweatherapp.model.CitiesDao
import com.solutions.openweatherapp.network.authentication.WeatherAPI
import com.solutions.openweatherapp.network.repository.IWeatherRepository
import com.solutions.openweatherapp.network.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by anjalisingh on 14,January,2019
 */

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesAppDatabase(context: Context) : CitiesDB {
        return Room.databaseBuilder(
            context, CitiesDB::class.java, "database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun providesCitiesDAO(database: CitiesDB) : CitiesDao {
        return database.citiesDao()
    }

    @Singleton
    @Provides
    fun providesWeatherRepository(api : WeatherAPI, citiesDao : CitiesDao) : IWeatherRepository = WeatherRepositoryImpl(api, citiesDao)

}