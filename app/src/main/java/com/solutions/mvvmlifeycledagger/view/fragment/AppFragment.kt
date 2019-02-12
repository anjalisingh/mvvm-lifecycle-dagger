package com.solutions.mvvmlifeycledagger.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.android.support.AndroidSupportInjection

/**
 * Created by anjalisingh on 14,January,2019
 */

abstract class AppFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    protected fun navController(): NavController =
        requireNotNull(view?.findNavController()) {
            "NavController not found"
        }
}