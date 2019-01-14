package com.solutions.openweatherapp.network.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.MediatorLiveData

import com.solutions.openweatherapp.WeatherApp
import com.solutions.openweatherapp.network.authentication.WeatherAPI
import javax.inject.Inject
import com.google.gson.Gson
import com.solutions.openweatherapp.model.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton


/**
 * Created by anjalisingh on 14,January,2019
 */

@Singleton
class WeatherRepositoryImpl
    @Inject
    constructor(
        val weatherApi: WeatherAPI,
        val citiesDao : CitiesDao
)
    : IWeatherRepository {

    override fun saveCity(city: CityModel) {
        citiesDao.insert(city)
    }

    override fun removeCity(city: CityModel) {
        citiesDao.remove(city)
    }

    override fun getCities(): LiveData<List<CityModel>> {
        return citiesDao.getCities()
    }

    private val compositeDisposable = CompositeDisposable()

    override fun fetchWeatherBySearch(city : String): LiveData<Resource<WeatherModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchWeatherByCoords(lat: Double, long: Double): LiveData<Resource<WeatherModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchCitiesInCycle(lat: Double, long: Double): LiveData<Resource<CitiesModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchWeatherForecast(id: Long): LiveData<Resource<ForecastModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchWeatherByZipcode(params: HashMap<String, String>): LiveData<Resource<WeatherModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchWeatherByCityID(params: HashMap<String, String>): LiveData<Resource<WeatherModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchCitiesByBoundingBox(params: HashMap<String, String>): LiveData<Resource<CitiesModel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearDisposables() {
        compositeDisposable.clear()
    }
}
