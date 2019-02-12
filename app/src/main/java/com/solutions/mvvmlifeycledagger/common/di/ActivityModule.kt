package com.solutions.mvvmlifeycledagger.common.di

import com.solutions.mvvmlifeycledagger.view.MainActivity
import com.solutions.mvvmlifeycledagger.view.activity.FavoriteLocationActivity
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