package com.solutions.openweatherapp.utils

import android.view.animation.BounceInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator


/**
 * Created by anjalisingh on 14,January,2019
 */

class CustomBounceInterpolator(private val timeDivider: Float) : Interpolator {
    private val a: AccelerateInterpolator
    private val b: BounceInterpolator

    init {
        a = AccelerateInterpolator()
        b = BounceInterpolator()
    }

    override fun getInterpolation(t: Float): Float {
        return if (t < timeDivider)
            a.getInterpolation(t)
        else
            b.getInterpolation(t)
    }

}