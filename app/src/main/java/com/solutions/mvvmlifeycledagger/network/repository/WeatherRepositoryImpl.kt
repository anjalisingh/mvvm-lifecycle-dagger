package com.solutions.mvvmlifeycledagger.network.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.solutions.mvvmlifeycledagger.model.CitiesDao
import com.solutions.mvvmlifeycledagger.model.CitiesModel
import com.solutions.mvvmlifeycledagger.model.CityModel
import com.solutions.mvvmlifeycledagger.model.ForecastModel
import com.solutions.mvvmlifeycledagger.model.WeatherModel
import com.solutions.mvvmlifeycledagger.network.authentication.WeatherAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
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

        val data = MutableLiveData<Resource<WeatherModel>>()

        compositeDisposable.add(
            weatherApi.fetchWeatherBySearch(city)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<WeatherModel>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: WeatherModel) {
                        data.value = Resource.success(t)
                    }

                    override fun onError(e: Throwable) {
                        if (e is RetrofitException) {
                            data.setValue(Resource.exception(e))
                        } else {
                            data.setValue(Resource.error(e as Resource.AppException))
                        }
                    }
                }
                ))

        return data

    }

    override fun fetchWeatherForecast(id: Long): LiveData<Resource<ForecastModel>> {
        val data = MutableLiveData<Resource<ForecastModel>>()

        compositeDisposable.add(
            weatherApi.fetchWeatherForecast(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<ForecastModel>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: ForecastModel) {
                        data.value = Resource.success(t)
                    }

                    override fun onError(e: Throwable) {
                        if (e is RetrofitException) {
                            data.setValue(Resource.exception(e))
                        } else {
                            data.setValue(Resource.error(e as Resource.AppException))
                        }
                    }
                }
                ))

        return data
    }

    override fun fetchWeatherByCoords(lat : Double, long : Double): LiveData<Resource<WeatherModel>> {
        val data = MutableLiveData<Resource<WeatherModel>>()

        compositeDisposable.add(
            weatherApi.fetchWeatherByCoords(lat, long)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<WeatherModel>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: WeatherModel) {
                        data.value = Resource.success(t)
                    }

                    override fun onError(e: Throwable) {
                        if (e is RetrofitException) {
                            data.setValue(Resource.exception(e))
                        } else {
                            data.setValue(Resource.error(e as Resource.AppException))
                        }
                    }
                }
                ))

        return data
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

    override fun fetchCitiesInCycle(lat : Double, long : Double): LiveData<Resource<CitiesModel>> {
        val data = MutableLiveData<Resource<CitiesModel>>()

        compositeDisposable.add(
            weatherApi.fetchCitiesInCycle(lat, long)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<CitiesModel>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: CitiesModel) {
                        data.value = Resource.success(t)
                    }

                    override fun onError(e: Throwable) {
                        if (e is RetrofitException) {
                            data.setValue(Resource.exception(e))
                        } else {
                            data.setValue(Resource.error(e as Resource.AppException))
                        }
                    }
                }
                ))

        return data
    }

    override fun clearDisposables() {
        compositeDisposable.clear()
    }
}
