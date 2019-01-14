package com.solutions.openweatherapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by anjalisingh on 14,January,2019
 */

data class CitiesModel(
    @SerializedName("message") val message : String,
    @SerializedName("cod") val cod : Int,
    @SerializedName("count") val count : Int,
    @SerializedName("list") val list : ArrayList<WeatherModel>
)