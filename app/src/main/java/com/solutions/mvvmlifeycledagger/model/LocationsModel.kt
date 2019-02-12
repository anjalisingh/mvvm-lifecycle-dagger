package com.solutions.mvvmlifeycledagger.model

import com.google.gson.annotations.SerializedName

data class LocationsModel(
    @SerializedName("list") val list : ArrayList<CityModel>
)