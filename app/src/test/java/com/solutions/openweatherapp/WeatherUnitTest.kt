package com.solutions.openweatherapp

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.solutions.openweatherapp.api.WeatherGeneratorApi
import com.solutions.openweatherapp.network.repository.IWeatherRepository
import com.solutions.openweatherapp.viewmodel.MainViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock


/**
 * Created by anjalisingh on 14,January,2019
 */

class WeatherUnitTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val weatherRepo = mock(IWeatherRepository::class.java)
    @Mock lateinit var viewModel: MainViewModel
    @Before
    fun setUp() {
        viewModel = MainViewModel(weatherRepo)
    }

    @Test
    fun test_now_weather() {
        viewModel?.setWeatherVM(WeatherGeneratorApi.getWeatherViewModel())

        assertTrue(viewModel?.getWeatherVM()?.cityName != null)
        assertEquals("London", viewModel?.getWeatherVM()?.cityName)
        assertNotNull(viewModel?.getWeatherVM()?.id)

        assertTrue(viewModel?.getWeatherVM()?.weather != null)
        assert(viewModel?.getWeatherVM()?.weather?.isEmpty() == false)
        assertEquals("Drizzle", viewModel?.getWeatherVM()?.weather?.get(0)?.main)

        assertFalse(viewModel?.getWeatherVM()?.mainConditions?.temp == null)
        assertNotNull(viewModel?.getWeatherVM()?.mainConditions?.humidity)

        assertNull(viewModel?.getWeatherVM()?.clouds)
        assertNotNull(viewModel?.getWeatherVM()?.rain)
    }

    @Test
    fun test_forecase_weather() {
        viewModel?.setForecast(WeatherGeneratorApi.getWeatherForecastViewModel())

        assertTrue(viewModel?.getForecastVM()?.city != null)
        assertEquals("London", viewModel?.getForecastVM()?.city?.name)

        assertNotNull(viewModel?.getForecastVM()?.cnt)
        assertEquals(2, viewModel?.getForecastVM()?.cnt)

        assertTrue(viewModel?.getForecastVM()?.forecastList != null)
        assertTrue(viewModel?.getForecastVM()?.forecastList?.isEmpty() == false)
        assertEquals("Greater London", viewModel?.getForecastVM()?.forecastList?.get(0)?.cityName)
        assertEquals("Newtonhill", viewModel?.getForecastVM()?.forecastList?.get(1)?.cityName)

        assert(viewModel?.getForecastVM()?.forecastList?.get(0)?.weather?.isEmpty() == false)
        assertEquals("Drizzle", viewModel?.getForecastVM()?.forecastList?.get(0)?.weather?.get(0)?.main)

        assertFalse(viewModel?.getForecastVM()?.forecastList?.get(0)?.mainConditions?.temp == null)
        assertNotNull(viewModel?.getForecastVM()?.forecastList?.get(0)?.mainConditions?.humidity)

        assertNull(viewModel?.getForecastVM()?.forecastList?.get(0)?.clouds)
        assertNotNull(viewModel?.getForecastVM()?.forecastList?.get(0)?.rain)
    }

    @Test
    fun test_map_cycle() {
        viewModel?.setWeatherMapList(WeatherGeneratorApi.getMapCycleWeatherViewModel())

        assertTrue(viewModel?.getWeatherCycleVM()?.list != null)
        assertTrue(viewModel?.getWeatherCycleVM()?.list?.isEmpty() == false)
        assertEquals("London", viewModel?.getWeatherCycleVM()?.list?.get(0)?.cityName)
        assertEquals("Newtonhill", viewModel?.getWeatherCycleVM()?.list?.get(1)?.cityName)

        assert(viewModel?.getWeatherCycleVM()?.list?.get(0)?.weather?.isEmpty() == false)
        assertEquals("Drizzle", viewModel?.getWeatherCycleVM()?.list?.get(0)?.weather?.get(0)?.main)

        assertFalse(viewModel?.getWeatherCycleVM()?.list?.get(0)?.mainConditions?.temp == null)
        assertNotNull(viewModel?.getWeatherCycleVM()?.list?.get(0)?.mainConditions?.humidity)

        assertNull(viewModel?.getWeatherCycleVM()?.list?.get(0)?.clouds)
        assertNotNull(viewModel?.getWeatherCycleVM()?.list?.get(0)?.rain)
    }

}