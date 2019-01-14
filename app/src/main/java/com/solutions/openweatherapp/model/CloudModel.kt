package com.solutions.openweatherapp.model

import com.google.gson.annotations.SerializedName

data class CloudModel(
    @SerializedName("all") val all : Int
)
