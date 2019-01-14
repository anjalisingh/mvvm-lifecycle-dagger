package com.solutions.openweatherapp.common.di

import com.solutions.openweatherapp.view.MainActivity
import com.solutions.openweatherapp.view.activity.FavoriteLocationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by anjalisingh on 14,January,2019
 */

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(FragmentModule::class))
    abstract fun contributeFavoriteLocation() : FavoriteLocationActivity
}