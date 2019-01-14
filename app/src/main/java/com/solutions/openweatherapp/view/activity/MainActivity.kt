package com.solutions.openweatherapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.solutions.openweatherapp.R
import com.solutions.openweatherapp.model.CityModel
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.app_bar_main.*
import javax.inject.Inject
import android.widget.ArrayAdapter
import co.realpost.android.common.persistance.UPSharedPrefs
import com.solutions.openweatherapp.common.ui.BaseActivity
import com.solutions.openweatherapp.view.activity.FavoriteLocationActivity
import com.solutions.openweatherapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : BaseActivity(), HasSupportFragmentInjector ,  NavigationView.OnNavigationItemSelectedListener {

    companion object {
        var currentLocation = "Singapore"
    }

    @Inject lateinit var sharedPrefs : UPSharedPrefs

    override val layoutResource: Int = R.layout.activity_main
    override val homeUpEnabled: Boolean = false
    override val needToolbar: Boolean = true
    override val toolbarText: String = ""

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        AndroidInjection.inject(this)

        val sharedPrefLocation = sharedPrefs?.getPermanentPrefsStringValue(getString(R.string.key_current_city))

        tvSearchCity.setOnClickListener {
            val intent = Intent(this, FavoriteLocationActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getAllCities()
            .observe(this,
                Observer<List<CityModel>> { cities ->
                    cities?.let {

                        if(it.isEmpty()) {
                            // add default location as Singapore if empty
                            sharedPrefs.setPermanentPrefsStringValue(getString(R.string.key_current_city), currentLocation)
                            viewModel.saveCity(CityModel(currentLocation))
                        }

                        var listItems = ArrayList<String>()
                        it.toMutableList()
                            ?.filter {
                                listItems.add(it?.name?.capitalize())
                            }
                    }
                })

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
