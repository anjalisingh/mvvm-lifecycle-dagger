package com.solutions.mvvmlifeycledagger.model

import com.google.gson.annotations.SerializedName

data class Conditions(
    @SerializedName("temp") val temp : Double,
    @SerializedName("pressure") val pressure : Double,
    @SerializedName("humidity") val humidity : Double,
    @SerializedName("temp_min") val tempMin : Double,
    @SerializedName("temp_max") val tempMax : Double

)
