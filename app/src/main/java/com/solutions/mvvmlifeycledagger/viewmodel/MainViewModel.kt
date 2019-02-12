package com.solutions.mvvmlifeycledagger.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.solutions.mvvmlifeycledagger.model.CitiesModel
import com.solutions.mvvmlifeycledagger.model.CityModel
import com.solutions.mvvmlifeycledagger.model.Coordinates
import com.solutions.mvvmlifeycledagger.model.ForecastModel
import com.solutions.mvvmlifeycledagger.model.WeatherModel
import com.solutions.mvvmlifeycledagger.network.repository.IWeatherRepository
import com.solutions.mvvmlifeycledagger.network.repository.Resource
import com.solutions.mvvmlifeycledagger.network.repository.RetrofitException
import com.solutions.mvvmlifeycledagger.utils.RetrofitErrorUtils
import java.lang.Exception
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val repository: IWeatherRepository
) : ViewModel() {


    private val city: MutableLiveData<String> = MutableLiveData()
    private val cityId: MutableLiveData<Long> = MutableLiveData()
    private var model : WeatherModel ?= null
    private var coords : MutableLiveData<Coordinates> = MutableLiveData()

    private var weatherByCityResponse:
        LiveData<Resource<WeatherModel>> = Transformations.switchMap( city
    ) { city ->
        return@switchMap repository.fetchWeatherBySearch(city)
    }

    private var weatherForecastResponse:
        LiveData<Resource<ForecastModel>> = Transformations.switchMap( cityId
    ) { cityId ->
        return@switchMap repository.fetchWeatherForecast(cityId)
    }

    private var weatherMapResponse :  LiveData<Resource<CitiesModel>> = Transformations.switchMap( coords
    ) { coords ->
        return@switchMap repository.fetchCitiesInCycle(coords.latitude, coords.longitude)
    }

    fun setCurrentLocation(city : String) {
        this.city.value = city
    }

    // retrieve weather LiveData
    fun getWeather():  LiveData<Resource<WeatherModel>> {
        return weatherByCityResponse
    }

    fun getWeatherForecast() : LiveData<Resource<ForecastModel>> {

        return weatherForecastResponse
    }

    fun setWeatherVM(data: WeatherModel?) {
        this.model = data
        data?.id?.let {
            this.cityId.value = it
        }

        data?.coords?.let {
            this.coords.value = Coordinates(it.latitude, it.longitude)
        }
    }

    fun getWeatherVM(): WeatherModel? {
        return model
    }

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

    fun getErrorMessage(error: Resource.AppException?): String? {
        return error?.localizedMessage
    }

    fun getExceptionMessage(exception: RetrofitException?): String {
        var errorMessage = "Error!"
        try {
            val error = exception as RetrofitException
            if (error.kind == RetrofitException.Kind.HTTP) {
                // parsing error json to pojo
                val errorResponse = RetrofitErrorUtils.parseError(error)
                Log.e("Error response %s", errorResponse?.errorMessage)
                errorResponse?.errorMessage?.apply { errorMessage = this }
            } else if (error.kind == RetrofitException.Kind.NETWORK) {
                Log.e("Error response %s", error.localizedMessage)
                errorMessage = "We're having trouble connecting to our servers. Please check your connection and try again."
            } else if(error.kind != null) {
                errorMessage = error?.localizedMessage
            }
        } catch (e1: Exception) {
            e1?.printStackTrace()
            errorMessage = e1?.localizedMessage
        }

        return errorMessage
    }

    private var forecastModel: ForecastModel? = null

    fun setForecast(model: ForecastModel?) {
        model.let {
            forecastModel = it
        }
    }

    fun getForecastVM() : ForecastModel? {
        return forecastModel
    }

    fun getWeatherCycle(): LiveData<Resource<CitiesModel>> {
        return weatherMapResponse
    }

    private lateinit var weatherMap: CitiesModel

    fun setWeatherMapList(data: CitiesModel?) {
        data?.list?.let {
            weatherMap = data
        }
    }

    fun getWeatherCycleVM(): CitiesModel? {
        return weatherMap
    }

}
