package com.solutions.openweatherapp.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import javax.inject.Singleton

/**
 * Created by anjalisingh on 14,January,2019
 */

@Database( entities = arrayOf(CityModel::class), version = 2 )
abstract class CitiesDB : RoomDatabase() {
    abstract fun citiesDao(): CitiesDao
}