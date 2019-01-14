package com.solutions.openweatherapp.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.solutions.openweatherapp.R
import com.solutions.openweatherapp.model.WeatherModel
import kotlinx.android.synthetic.main.map_preview.view.tvCityName
import kotlinx.android.synthetic.main.map_preview.view.tvDescription
import kotlinx.android.synthetic.main.map_preview.view.tvTemperature


/**
 * Created by anjalisingh on 14,January,2019
 */
class InfoMapAdapter(val context : Context) : GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View? {
        return null
    }

    override fun getInfoWindow(marker: Marker?): View {
        val view = (context as Activity)?.layoutInflater.inflate(R.layout.map_preview, null)

        val model = marker?.tag as WeatherModel

        view?.tvCityName?.text = model?.cityName?.toString()
        view?.tvTemperature?.text = (model?.mainConditions?.temp?.toString())?.plus(0x00B0.toChar() + "C")
        model?.weather?.get(0)?.apply {
            view?.tvDescription?.text = "|  " + this.main
        }

        return view
    }


}