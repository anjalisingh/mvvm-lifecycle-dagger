package com.solutions.openweatherapp.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import co.realpost.android.common.persistance.UPSharedPrefs
import com.solutions.openweatherapp.R
import com.solutions.openweatherapp.model.CityModel
import com.solutions.openweatherapp.ui.adapter.DeleteLocationListener
import com.solutions.openweatherapp.ui.adapter.LocationsAdapter
import com.solutions.openweatherapp.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_search_location.etLocation
import kotlinx.android.synthetic.main.activity_search_location.rvLocationList
import kotlinx.android.synthetic.main.view_toolbar_layout.toolbar
import javax.inject.Inject


/**
 * Created by anjalisingh on 14,January,2019
 */

class FavoriteLocationActivity :  AppCompatActivity(), HasSupportFragmentInjector, DeleteLocationListener {
    override fun onDeleteLocation(city: CityModel) {
        if(city.name == (sharedPrefs.getPermanentPrefsStringValue(getString(R.string.key_current_city)))) {
          sharedPrefs.setPermanentPrefsStringValue(getString(R.string.key_current_city), "")
        }
        viewModel.removeCity(city = city)
    }

    companion object {
       const val TAG = "FavoriteLocationActivity"
    }
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    lateinit var searchListAdapter : LocationsAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel : MainViewModel
    @Inject lateinit var sharedPrefs : UPSharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        setContentView(R.layout.activity_search_location)

        initToolbar()
        rvLocationList?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        searchListAdapter = LocationsAdapter(this, this,false)
        rvLocationList.addItemDecoration(searchListAdapter.ItemDecoration(this))
        rvLocationList?.adapter = searchListAdapter


        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getAllCities()
            .observe(this,
            Observer<List<CityModel>> { cities ->
                cities?.let { searchListAdapter?.items = it.toMutableList() }
            })

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        etLocation.setOnEditorActionListener { v, actionId, event ->
            val searchText = v.editableText.toString()
            if(searchText?.isEmpty()) false

            else if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                val city = CityModel(searchText?.toLowerCase())
                viewModel.saveCity(city)
                sharedPrefs.setPermanentPrefsStringValue(getString(R.string.key_current_city), city.name)

                v.clearComposingText()
                v.text = ""
                v.clearFocus()
                inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                true
            } else {
                false
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

  private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)

            actionBar.title = "Favorite Cities"
        }
    }
}
