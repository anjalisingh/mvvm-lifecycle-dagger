package com.solutions.mvvmlifeycledagger.model

import com.google.gson.annotations.SerializedName

data class SnowModel(
    @SerializedName("1h") val oneVol : Double?,
    @SerializedName("3h") val threeVol : Double?
)
