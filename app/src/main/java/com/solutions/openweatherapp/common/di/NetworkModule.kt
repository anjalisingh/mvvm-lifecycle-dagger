package com.solutions.openweatherapp.common.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.solutions.openweatherapp.BuildConfig
import com.solutions.openweatherapp.network.authentication.RxErrorHandlingCallAdapterFactory
import com.solutions.openweatherapp.network.authentication.WeatherAPI
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = arrayOf(
    ViewModelModule::class))
class NetworkModule {

    companion object {
        private val BASE_URL = "https://api.openweathermap.org/"
        private val API_KEY = "245019f0c5556a7904e38b49589c6ae5"
    }


    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor {
                val url = it.request().url().newBuilder()
                    .addQueryParameter("APPID", API_KEY)
                    .build()
                val request = it.request().newBuilder()
                    .url(url)
                    .build()
                it.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .client(client.build())
            .build()
    }

    @Provides
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherAPI {
        return retrofit.create(WeatherAPI::class.java)
    }


}