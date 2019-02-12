package com.solutions.mvvmlifeycledagger.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solutions.mvvmlifeycledagger.R
import com.solutions.mvvmlifeycledagger.common.ui.BaseActivity
import com.solutions.mvvmlifeycledagger.model.ForecastModel
import com.solutions.mvvmlifeycledagger.ui.adapter.ForecastWeatherAdapter
import com.solutions.mvvmlifeycledagger.view.fragment.AppFragment
import kotlinx.android.synthetic.main.forecase_fragment.rvForecast

/**
 * Created by anjalisingh on 14,January,2019
 */

class ForecastWeatherFragment : AppFragment() {

    fun refreshData(model : ForecastModel) {
        model?.forecastList?.let {
            forecastForecastWeatherAdapter?.items = it?.toMutableList()
        }
    }

    companion object {
        fun newInstance() = ForecastWeatherFragment()
        val TAG = "ForecastWeatherFragment"
    }

    lateinit var forecastForecastWeatherAdapter: ForecastWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.forecase_fragment, container, false)
    }

    lateinit var  mCallback : ForecastWeatherListener
    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as ForecastWeatherListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement ForecastWeatherListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        rvForecast?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        forecastForecastWeatherAdapter = ForecastWeatherAdapter(activity as BaseActivity)
        rvForecast?.adapter = forecastForecastWeatherAdapter

        mCallback?.onForecastFragmentAttached()
    }

    interface ForecastWeatherListener {
        fun onForecastFragmentAttached()
    }
}