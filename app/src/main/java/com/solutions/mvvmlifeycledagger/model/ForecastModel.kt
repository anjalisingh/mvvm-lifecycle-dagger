package com.solutions.mvvmlifeycledagger.model

import com.google.gson.annotations.SerializedName

/**
 * Created by anjalisingh on 14,January,2019
 */

data class ForecastModel(
    @SerializedName("city") val city : CityModel?,
    @SerializedName("cnt") val cnt : Int,
    @SerializedName("list") val forecastList : ArrayList<WeatherModel>
    )

data class ForecastWeather(
    @SerializedName("dt") val date : Long?,
    @SerializedName("weather") val weather : List<Weather>?,
    @SerializedName("visibility") val visibility : Double?,
    @SerializedName("wind") val wind : WindModel?,
    @SerializedName("clouds") val clouds : String?,
    @SerializedName("rain") val rain : RainModel?,
    @SerializedName("snow") val snow : Double?,
    @SerializedName("speed") val speed : Double?,
    @SerializedName("humidity") val humidity : Double?,
    @SerializedName("pressure") val pressure : Double?

)
