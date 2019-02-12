package com.solutions.mvvmlifeycledagger.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import com.solutions.mvvmlifeycledagger.R
import com.solutions.mvvmlifeycledagger.model.WeatherModel
import com.solutions.mvvmlifeycledagger.utils.CustomBounceInterpolator
import com.solutions.mvvmlifeycledagger.utils.MiscUtils
import com.solutions.mvvmlifeycledagger.view.fragment.AppFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.now_fragment.ivIcon
import kotlinx.android.synthetic.main.now_fragment.tvCityName
import kotlinx.android.synthetic.main.now_fragment.tvDescription
import kotlinx.android.synthetic.main.now_fragment.tvHumidity
import kotlinx.android.synthetic.main.now_fragment.tvLastUpdated
import kotlinx.android.synthetic.main.now_fragment.tvMaxTemperature
import kotlinx.android.synthetic.main.now_fragment.tvMinTemperature
import kotlinx.android.synthetic.main.now_fragment.tvPressure
import kotlinx.android.synthetic.main.now_fragment.tvTemperature


/**
 * Created by anjalisingh on 14,January,2019
 */

class NowFragment : AppFragment() {

    companion object {
        fun newInstance() = NowFragment()
        val TAG = "NowFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.now_fragment, container, false)
    }

    lateinit var  mCallback : NowListener
    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            mCallback = context as NowListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement NowListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mCallback?.onNowFragmentAttached()
    }

    fun refreshData(model: WeatherModel) {
        tvCityName?.text = model?.cityName?.toString()
        tvTemperature?.text = (model?.mainConditions?.temp?.toString())?.plus(0x00B0.toChar() + "C")
        model?.weather?.get(0)?.apply {
            tvDescription?.text = this.main
            context?.let {
                if(ivIcon != null) {
                    Picasso.with(context)
                        .load(getString(R.string.icon_url, this?.icon))
                        .placeholder(R.drawable.placeholder_cloud)
                        .into(ivIcon)
                }
            }

            val animation = TranslateAnimation(-100f, 100f, 0f, 0f)
            animation.duration = 1500
            animation.interpolator = CustomBounceInterpolator(500f)
            ivIcon?.startAnimation(animation)
        }
        tvMinTemperature?.text = getString(R.string.min_temp, model?.mainConditions?.tempMin?.toString() ?: "-")
        tvMaxTemperature?.text = getString(R.string.max_temp, model?.mainConditions?.tempMax?.toString() ?: "-")
        tvPressure?.text = getString(R.string.pressure, model?.mainConditions?.pressure?.toString()  ?: "-")
        tvHumidity?.text = getString(R.string.humidity, model?.mainConditions?.humidity?.toString()?.plus(" %") ?: "-")
        tvLastUpdated?.text = getString(R.string.last_updated, MiscUtils.getTime(model?.date, true) ?: "-")
    }

    interface NowListener {
        fun onNowFragmentAttached()
    }
}
