package com.solutions.openweatherapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity( tableName = "cities" )
data class CityModel(
    @PrimaryKey
    @SerializedName("name") val name : String

)
