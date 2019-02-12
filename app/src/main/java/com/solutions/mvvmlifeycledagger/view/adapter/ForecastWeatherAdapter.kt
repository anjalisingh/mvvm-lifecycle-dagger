package com.solutions.mvvmlifeycledagger.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.solutions.mvvmlifeycledagger.R
import com.solutions.mvvmlifeycledagger.common.ui.BaseActivity
import com.solutions.mvvmlifeycledagger.model.WeatherModel
import com.solutions.mvvmlifeycledagger.utils.MiscUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.forecast_item.view.*

/**
 * Created by anjalisingh on 14,January,2019
 */
class ForecastWeatherAdapter(val mActivity : BaseActivity) : BaseAdapter<ForecastViewHolder, WeatherModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val holder = ForecastViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.forecast_item, parent, false)
        )

        return holder
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val option = mItems[position]

        holder?.itemView?.tvTemperature?.text = mActivity?.getString(R.string.temp_range, option?.mainConditions?.tempMin ?: "-" , option?.mainConditions?.tempMax ?: "-")
        holder?.itemView?.tvPressure?.text = mActivity?.getString(R.string.pressure, option?.mainConditions?.pressure?.toString() ?: "-")
        holder?.itemView?.tvHumidity?.text = mActivity?.getString(R.string.humidity, option?.mainConditions?.humidity?.toString() ?: "-")
        holder?.itemView?.tvSpeed?.text = mActivity?.getString(R.string.windspeed, option?.wind?.speed?.toString() ?: "-")
        holder?.itemView?.tvSnow?.text = mActivity?.getString(R.string.snow, option?.snow?.threeVol?.toString() ?: "-")
        holder?.itemView?.tvClouds?.text = mActivity?.getString(R.string.cloudiness, option?.clouds?.all?.toString() ?: "-")
        holder?.itemView?.tvDate?.text = MiscUtils.Companion?.getTime(option?.date)

        option?.weather?.get(0)?.apply {
            holder?.itemView?.tvDescription?.text = this.main
            Picasso.with(mActivity)
                .load(mActivity?.getString(R.string.icon_url, this.icon))
                .placeholder(R.drawable.placeholder_cloud)
                .into(holder?.itemView?.ivIcon)
        }
    }

}

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)