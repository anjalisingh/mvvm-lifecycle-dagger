package com.solutions.openweatherapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by anjalisingh on 14,January,2019
 */
data class ErrorResponse(
    @SerializedName("cod") val code : String?,
    @SerializedName("message") val errorMessage : String?,
    @SerializedName("error") val error : String?

)