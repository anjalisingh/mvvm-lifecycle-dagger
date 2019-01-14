package com.solutions.openweatherapp.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.solutions.openweatherapp.R
import com.solutions.openweatherapp.model.CitiesModel
import com.solutions.openweatherapp.model.ForecastModel
import com.solutions.openweatherapp.model.WeatherModel
import com.solutions.openweatherapp.network.repository.Resource
import com.solutions.openweatherapp.ui.adapter.ViewPagerAdapter
import com.solutions.openweatherapp.view.MainActivity
import com.solutions.openweatherapp.view.fragment.AppFragment
import com.solutions.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.main_tabs_layout.tvError
import javax.inject.Inject

/**
 * Created by anjalisingh on 14,January,2019
 */

class TabsFragment : AppFragment(){

    companion object {
        fun newInstance() = TabsFragment()
        val TAG = "TabsFragment"
    }

    private lateinit var viewPager : ViewPager
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel : MainViewModel
    private var tabFragments : HashMap<Int, Fragment>? = HashMap()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.main_tabs_layout, container, false)

        val tabLayout  : TabLayout?= layout.findViewById(R.id.tabs)
        viewPager = layout.findViewById(R.id.viewpager)
        tabLayout?.setupWithViewPager(viewPager)

        (activity as MainActivity).setFragmentRefreshListener(object : MainActivity.FragmentRefreshListener {
            override fun onRefresh(location : String) {
                viewModel?.setCurrentLocation(city = location)
            }

            override fun onReloadTab(tab: Tab) {
                refreshTab(tab)
            }

        })

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        var nowFragment = tabFragments?.get(Tab.NOW.ordinal)
        var forecastFragment = tabFragments?.get(Tab.FORECAST.ordinal)
        var mapFragment = tabFragments?.get(Tab.MAP.ordinal)

        if(tabFragments?.contains(Tab.NOW.ordinal) == false) {
            nowFragment = NowFragment.newInstance()
            tabFragments?.set(Tab.NOW.ordinal, nowFragment)
        }
        if(tabFragments?.contains(Tab.FORECAST.ordinal) == false) {
            forecastFragment = ForecastWeatherFragment.newInstance()
            tabFragments?.set(Tab.FORECAST.ordinal, forecastFragment)
        }
        if(tabFragments?.contains(Tab.MAP.ordinal) == false) {
            mapFragment = MapFragment.newInstance()
            tabFragments?.set(Tab.MAP.ordinal, mapFragment)
        }

        nowFragment?.let { adapter.addFrag(it, "NOW") }
        forecastFragment?.let { adapter.addFrag(it, "FORECAST") }
        mapFragment?.let { adapter.addFrag(it, "MAP") }
        viewPager?.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel?.getWeather()?.observe(this,
            Observer<Resource<WeatherModel>> { resource ->
                if (resource != null) {
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            Log.d(NowFragment.TAG,resource.data.toString() )
                            viewModel?.setWeatherVM(resource?.data)
                            refreshTab(Tab.NOW)

                            viewPager?.visibility = View.VISIBLE
                            tvError?.visibility = View.GONE
                        }

                        Resource.Status.RETROFIT_ERROR -> {
//                            Toast.makeText(context, viewModel?.getExceptionMessage(resource?.exception), Toast.LENGTH_LONG).show()
                            tvError?.text = viewModel?.getExceptionMessage(resource?.exception)
                            viewPager?.visibility = View.GONE
                            tvError?.visibility = View.VISIBLE
                        }

                        Resource.Status.ERROR -> {
//                            Toast.makeText(context, viewModel?.getErrorMessage(resource?.error), Toast.LENGTH_LONG).show()
                            tvError?.text = viewModel?.getErrorMessage(resource?.error)
                            viewPager?.visibility = View.GONE
                            tvError?.visibility = View.VISIBLE
                        }

                    }
                }
            })

        viewModel?.getWeatherForecast()?.observe(this,
            Observer<Resource<ForecastModel>> { resource ->
                if (resource != null) {
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            viewModel?.setForecast(resource.data)
                            refreshTab(Tab.FORECAST)
                        }

                        Resource.Status.RETROFIT_ERROR -> {
                            Toast.makeText(context, viewModel?.getExceptionMessage(resource?.exception), Toast.LENGTH_LONG).show()

                        }

                        Resource.Status.ERROR -> {
                            Toast.makeText(context, viewModel?.getErrorMessage(resource?.error), Toast.LENGTH_LONG).show()

                        }

                    }
                }
            })

        viewModel?.getWeatherCycle()?.observe(
            this, Observer<Resource<CitiesModel>> { resource ->

                if (resource != null) {
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            viewModel?.setWeatherMapList(resource.data)
                            refreshTab(Tab.MAP)
                        }

                        Resource.Status.RETROFIT_ERROR -> {
                            Toast.makeText(context, viewModel?.getExceptionMessage(resource?.exception), Toast.LENGTH_LONG).show()

                        }

                        Resource.Status.ERROR -> {
                            Toast.makeText(context, viewModel?.getErrorMessage(resource?.error), Toast.LENGTH_LONG).show()

                        }

                    }
                }
            }
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabFragments?.clear()
    }

    private fun refreshTab(tab : Tab) {
        val frag = tabFragments?.get(tab.ordinal)
        if(frag is NowFragment) viewModel?.getWeatherVM()?.let { frag.refreshData(it) }
        if(frag is ForecastWeatherFragment) viewModel?.getForecastVM()?.let { frag.refreshData(it) }
        if(frag is MapFragment) viewModel?.getWeatherCycleVM()?.let { frag.refreshData(it , viewModel?.getWeatherVM()?.coords) }

    }

    enum class Tab  constructor(private val title: String) {
        NOW("NOW"), FORECAST("FORECAST"), MAP("Map")
    }
}