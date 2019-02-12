package com.solutions.mvvmlifeycledagger.model

import com.google.gson.annotations.SerializedName

data class WindModel(
    @SerializedName("speed") val speed : Double?,
    @SerializedName("deg") val deg : Double?
)