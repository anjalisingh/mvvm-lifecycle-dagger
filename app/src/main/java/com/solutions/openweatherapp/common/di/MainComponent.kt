package com.solutions.openweatherapp.common.di

import android.content.Context
import com.solutions.openweatherapp.WeatherApp
import com.solutions.openweatherapp.common.di.ActivityModule
import com.solutions.openweatherapp.common.di.AppModule
import com.solutions.openweatherapp.common.di.DataModule
import dagger.BindsInstance

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    DataModule::class])

public interface MainComponent {

    fun inject(app : WeatherApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: WeatherApp): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): MainComponent
    }
}