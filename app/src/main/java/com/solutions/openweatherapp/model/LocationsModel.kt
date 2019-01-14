package com.solutions.openweatherapp.model

import com.google.gson.annotations.SerializedName

data class LocationsModel(
    @SerializedName("list") val list : ArrayList<CityModel>
)