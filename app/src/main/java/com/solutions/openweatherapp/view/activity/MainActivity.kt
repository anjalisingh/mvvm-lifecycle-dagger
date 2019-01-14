package com.solutions.openweatherapp.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import co.realpost.android.common.persistance.UPSharedPrefs
import com.solutions.openweatherapp.R
import com.solutions.openweatherapp.common.ui.BaseActivity
import com.solutions.openweatherapp.model.CityModel
import com.solutions.openweatherapp.ui.fragment.ForecastWeatherFragment
import com.solutions.openweatherapp.ui.fragment.MapFragment
import com.solutions.openweatherapp.ui.fragment.NowFragment
import com.solutions.openweatherapp.ui.fragment.TabsFragment
import com.solutions.openweatherapp.view.activity.FavoriteLocationActivity
import com.solutions.openweatherapp.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.app_bar_main.spNavigation
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector,
    AdapterView.OnItemSelectedListener, MapFragment.MapListener, NowFragment.NowListener, ForecastWeatherFragment.ForecastWeatherListener {

    companion object {
        var currentLocation = "Singapore"
        val ACTION_ADD_CITY = 200
    }

    @Inject lateinit var sharedPrefs : UPSharedPrefs

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentLocation = parent?.getItemAtPosition(position).toString()
        sharedPrefs.setPermanentPrefsStringValue(getString(R.string.key_current_city), currentLocation)
        refreshTabs()
    }

    private fun refreshTabs() {
        if(getFragmentRefreshListener() != null) {
            getFragmentRefreshListener()?.onRefresh(currentLocation)
        }
    }

    private var fragmentRefreshListener: FragmentRefreshListener? = null

    fun getFragmentRefreshListener(): FragmentRefreshListener? {
        return fragmentRefreshListener
    }

    fun setFragmentRefreshListener(fragmentRefreshListener: FragmentRefreshListener?) {
        this.fragmentRefreshListener = fragmentRefreshListener
    }

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

  private var spinnerListItems = ArrayList<String>()
  private var originalItems = HashMap<String, String>()

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TabsFragment.newInstance())
                .commitAllowingStateLoss()
        }

        spNavigation.onItemSelectedListener = this

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getAllCities()
            .observe(this,
                Observer<List<CityModel>> { cities ->
                    cities?.let {

                        if(it.isEmpty()) {
                            // add default location as Singapore if empty
                            sharedPrefs.setPermanentPrefsStringValue(getString(R.string.key_current_city), currentLocation)
                            viewModel.saveCity(CityModel(currentLocation?.toLowerCase()))
                        }

                          spinnerListItems.clear()
                          originalItems.clear()
                          it.toMutableList()
                                  ?.filter {
                                    originalItems[it?.name?.toLowerCase()] = it?.name?.capitalize()
                                    spinnerListItems.add(it?.name?.capitalize())
                                  }
                          val dataAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, spinnerListItems)
                          dataAdapter.setDropDownViewResource(R.layout.spinner_item)
                          spNavigation.adapter = dataAdapter
                          spNavigation?.setSelection(dataAdapter.getPosition(originalItems[currentLocation]))
                    }
                })

    }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.search) {
      val intent = Intent(this, FavoriteLocationActivity::class.java)
      startActivityForResult(intent, ACTION_ADD_CITY)
      return true
    }

    return false
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    if(requestCode == ACTION_ADD_CITY) {
      currentLocation = sharedPrefs?.getPermanentPrefsStringValue(getString(R.string.key_current_city))!!
      refreshTabs()
    }

    super.onActivityResult(requestCode, resultCode, data)
  }

  override fun onNowFragmentAttached() {
        if(getFragmentRefreshListener() != null) {
            getFragmentRefreshListener()?.onReloadTab(TabsFragment.Tab.NOW)
        }
    }

    override fun onMapReady() {
        if(getFragmentRefreshListener() != null) {
            getFragmentRefreshListener()?.onReloadTab(TabsFragment.Tab.MAP)
        }
    }
    override fun onForecastFragmentAttached() {
        if(getFragmentRefreshListener() != null) {
            getFragmentRefreshListener()?.onReloadTab(TabsFragment.Tab.FORECAST)
        }
    }

    interface FragmentRefreshListener {
        fun onRefresh(city : String)

        fun onReloadTab(tab : TabsFragment.Tab)
    }
}
