package com.solutions.openweatherapp.model

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * Created by anjalisingh on 14,January,2019
 */

data class WeatherModel(
    @SerializedName("dt") val date : Long?,
    @SerializedName("coord") val coords: Coordinates?,
    @SerializedName("weather") val weather : List<Weather>?,
    @SerializedName("main") val mainConditions : Conditions?,
    @SerializedName("id") val id : Long?,
    @SerializedName("name") val cityName : String?,
    @SerializedName("visibility") val visibility : Double?,
    @SerializedName("wind") val wind : WindModel?,
    @SerializedName("clouds") val clouds : CloudModel?,
    @SerializedName("rain") val rain : RainModel?,
    @SerializedName("snow") val snow : SnowModel?

)