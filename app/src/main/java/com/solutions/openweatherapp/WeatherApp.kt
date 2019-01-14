package com.solutions.openweatherapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatDelegate
import com.solutions.openweatherapp.common.di.*
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by anjalisingh on 14,January,2019
 */

class WeatherApp : MultiDexApplication(), HasActivityInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    override fun onCreate() {
        super.onCreate()
        DaggerMainComponent
            .builder()
            .application(this)
            .context(applicationContext)
            .build()
            .inject(this)
    }


    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}