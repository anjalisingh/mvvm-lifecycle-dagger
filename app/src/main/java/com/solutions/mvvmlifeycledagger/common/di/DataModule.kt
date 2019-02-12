package com.solutions.mvvmlifeycledagger.common.di

import android.arch.persistence.room.Room
import android.content.Context
import com.solutions.mvvmlifeycledagger.model.CitiesDB
import com.solutions.mvvmlifeycledagger.model.CitiesDao
import com.solutions.mvvmlifeycledagger.network.authentication.WeatherAPI
import com.solutions.mvvmlifeycledagger.network.repository.IWeatherRepository
import com.solutions.mvvmlifeycledagger.network.repository.WeatherRepositoryImpl
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