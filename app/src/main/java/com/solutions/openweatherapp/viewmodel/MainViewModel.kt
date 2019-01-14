package com.solutions.openweatherapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.provider.Settings.Global.getString
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.solutions.openweatherapp.R
import com.solutions.openweatherapp.model.*
import com.solutions.openweatherapp.network.repository.IWeatherRepository
import com.solutions.openweatherapp.network.repository.Resource
import com.solutions.openweatherapp.network.repository.RetrofitException
import com.solutions.openweatherapp.network.repository.WeatherRepositoryImpl
import com.solutions.openweatherapp.utils.RetrofitErrorUtils
import java.lang.Exception
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val repository: IWeatherRepository
) : ViewModel() {

    private val city: MutableLiveData<String> = MutableLiveData()

    fun saveCity(city : CityModel) {
        repository.saveCity(city)
    }

    fun removeCity(city : CityModel) {
        repository.removeCity(city)
    }

    fun getAllCities() : LiveData<List<CityModel>> {
        return repository.getCities()
    }

    override fun onCleared() {
        repository?.clearDisposables()
    }

}
