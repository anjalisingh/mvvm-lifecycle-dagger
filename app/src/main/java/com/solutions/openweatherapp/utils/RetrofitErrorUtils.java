package com.solutions.openweatherapp.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solutions.openweatherapp.model.ErrorResponse;
import com.solutions.openweatherapp.network.repository.RetrofitException;

import java.io.IOException;

/**
 * Created by anjalisingh on 14,January,2019
 */
public class RetrofitErrorUtils {
    public static ErrorResponse parseError(RetrofitException exception) throws IOException {
        ErrorResponse errorResponse;

        JsonObject errorObject = exception.getErrorBodyAs(JsonObject.class);
        Gson gson = new Gson();
        errorResponse = gson.fromJson(errorObject, ErrorResponse.class);

        return errorResponse;
    }
}
