package com.solutions.mvvmlifeycledagger

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.solutions.mvvmlifeycledagger.common.di.DaggerMainComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
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