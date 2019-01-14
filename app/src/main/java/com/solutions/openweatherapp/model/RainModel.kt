package com.solutions.openweatherapp.model

import com.google.gson.annotations.SerializedName

data class RainModel(
    @SerializedName("1h") val oneVol : Double?,
    @SerializedName("3h") val threeVol : Double?
)
