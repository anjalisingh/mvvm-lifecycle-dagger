package com.solutions.mvvmlifeycledagger.network.repository

import android.arch.lifecycle.LiveData
import com.solutions.mvvmlifeycledagger.model.CitiesModel
import com.solutions.mvvmlifeycledagger.model.CityModel
import com.solutions.mvvmlifeycledagger.model.ForecastModel
import com.solutions.mvvmlifeycledagger.model.WeatherModel

/**
 * Created by anjalisingh on 14,January,2019
 */

interface IWeatherRepository {

    fun fetchWeatherBySearch(city : String): LiveData<Resource<WeatherModel>>

    fun fetchWeatherByCoords(lat : Double, long : Double): LiveData<Resource<WeatherModel>>

    fun fetchWeatherByZipcode(params: HashMap<String, String>): LiveData<Resource<WeatherModel>>

    fun fetchWeatherByCityID(params: HashMap<String, String>): LiveData<Resource<WeatherModel>>

    fun fetchCitiesByBoundingBox(params: HashMap<String, String>): LiveData<Resource<CitiesModel>>

    fun fetchCitiesInCycle(lat : Double, long : Double): LiveData<Resource<CitiesModel>>

    fun fetchWeatherForecast(id : Long) : LiveData<Resource<ForecastModel>>

    fun saveCity(city : CityModel)

    fun removeCity(city : CityModel)

    fun getCities() : LiveData<List<CityModel>>

    fun clearDisposables()
}