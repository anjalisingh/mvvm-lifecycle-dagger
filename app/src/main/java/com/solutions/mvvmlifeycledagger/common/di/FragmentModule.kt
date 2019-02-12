package com.solutions.mvvmlifeycledagger.common.di

import com.solutions.mvvmlifeycledagger.ui.fragment.ForecastWeatherFragment
import com.solutions.mvvmlifeycledagger.ui.fragment.MapFragment
import com.solutions.mvvmlifeycledagger.ui.fragment.NowFragment
import com.solutions.mvvmlifeycledagger.ui.fragment.TabsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by anjalisingh on 14,January,2019
 */

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MapFragment

    @ContributesAndroidInjector
    abstract fun contributeNowFragment() : NowFragment

    @ContributesAndroidInjector
    abstract fun contributeForecastFragment() : ForecastWeatherFragment

    @ContributesAndroidInjector
    abstract fun contributeTabsFragment() : TabsFragment
}
