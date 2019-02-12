package com.solutions.mvvmlifeycledagger.network.authentication

import com.solutions.mvvmlifeycledagger.model.CitiesModel
import com.solutions.mvvmlifeycledagger.model.ForecastModel
import com.solutions.mvvmlifeycledagger.model.WeatherModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by anjalisingh on 14,January,2019
 */

interface WeatherAPI {

    @GET("data/2.5/weather?units=metric")
    fun fetchWeatherBySearch(@Query("q") query: String) : Observable<WeatherModel>


    @GET("data/2.5/forecast?units=metric")
    fun fetchWeatherForecast(@Query("id") query: Long) : Observable<ForecastModel>

    @GET("data/2.5/weather?units=metric")
    fun fetchWeatherByCoords(@Query("lat") latitude: Double,
                             @Query("lon") longitude: Double) : Observable<WeatherModel>

    @GET("data/2.5/weather?units=metric")
    fun fetchWeatherByZipcode(@Query("zip") zip : String) : Observable<WeatherModel>

    @GET("data/2.5/weather?units=metric")
    fun fetchWeatherByCityID(@Query("id") id : String) : Observable<WeatherModel>

    @GET("data/2.5/box/city?units=metric")
    fun fetchCitiesByBoundingBox(@Query("bbox") bbox : String,
                                 @Query("cluster") cluster: Boolean,
                                 @Query("lang") lang : String
                                 ) : Observable<CitiesModel>

    @GET("data/2.5/find?units=metric&cnt=10&cluster=true")
    fun fetchCitiesInCycle(@Query("lon") longitude: Double,
                        @Query("lat") latitude: Double) : Observable<CitiesModel>



}