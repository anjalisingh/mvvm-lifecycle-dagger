package com.solutions.openweatherapp.api

import com.solutions.openweatherapp.model.CitiesModel
import com.solutions.openweatherapp.model.CityModel
import com.solutions.openweatherapp.model.Conditions
import com.solutions.openweatherapp.model.Coordinates
import com.solutions.openweatherapp.model.ForecastModel
import com.solutions.openweatherapp.model.RainModel
import com.solutions.openweatherapp.model.SnowModel
import com.solutions.openweatherapp.model.Weather
import com.solutions.openweatherapp.model.WeatherModel

/**
 * Created by anjalisingh on 14,January,2019
 */

class WeatherGeneratorApi {
    companion object {
        fun getWeatherViewModel(): WeatherModel {
            var weatherList = ArrayList<Weather>()
            weatherList.add(
                Weather(
                    id = 100,
                    main = "Drizzle",
                    description = "light drizzle",
                    icon = "09d"
                ))

            val weatherModel = WeatherModel(
                id = 2643743,
                cityName = "London",
                coords = Coordinates(-0.13, 51.51),
                mainConditions = Conditions(
                    temp = 280.32,
                    pressure = 1012.1,
                    humidity = 81.1,
                    tempMin = 12.0,
                    tempMax = 13.0
                ),
                weather = weatherList,
                clouds = null,
                rain = RainModel(12.1, 12.2),
                snow = SnowModel(1.1,2.1),
                date = 1485789600,
                visibility = 11.1,
                wind = null
            )

            return weatherModel
        }

        fun getWeatherForecastViewModel(): ForecastModel {
            var weatherList = ArrayList<Weather>()
            weatherList.add(
                Weather(
                    id = 100,
                    main = "Drizzle",
                    description = "light drizzle",
                    icon = "09d"
                ))

            val weatherModelFirst = WeatherModel(
                id = 2643743,
                cityName = "Greater London",
                coords = Coordinates(-0.13, 51.51),
                mainConditions = Conditions(
                    temp = 280.32,
                    pressure = 1012.1,
                    humidity = 81.1,
                    tempMin = 12.0,
                    tempMax = 13.0
                ),
                weather = weatherList,
                clouds = null,
                rain = RainModel(12.1, 12.2),
                snow = SnowModel(1.1,2.1),
                date = 1485789600,
                visibility = 11.1,
                wind = null
            )

            val weatherModelSecond = WeatherModel(
                id = 2641549,
                cityName = "Newtonhill",
                coords = Coordinates(-0.13, 51.51),
                mainConditions = Conditions(
                    temp = 280.32,
                    pressure = 1012.1,
                    humidity = 81.1,
                    tempMin = 12.0,
                    tempMax = 13.0
                ),
                weather = weatherList,
                clouds = null,
                rain = RainModel(12.1, 12.2),
                snow = SnowModel(1.1,2.1),
                date = 1521204600,
                visibility = 11.1,
                wind = null
            )

            var weatherModelList = ArrayList<WeatherModel>()
            weatherModelList.add(weatherModelFirst)
            weatherModelList.add(weatherModelSecond)
            var forecastModel = ForecastModel(
                cnt = 2,
                forecastList = weatherModelList,
                city = CityModel("London")
            )
            return forecastModel
        }

        fun getMapCycleWeatherViewModel(): CitiesModel {
            var weatherList = ArrayList<Weather>()
            weatherList.add(
                Weather(
                    id = 100,
                    main = "Drizzle",
                    description = "light drizzle",
                    icon = "09d"
                ))

            val weatherModelFirst = WeatherModel(
                id = 2643743,
                cityName = "London",
                coords = Coordinates(-0.13, 51.51),
                mainConditions = Conditions(
                    temp = 280.32,
                    pressure = 1012.1,
                    humidity = 81.1,
                    tempMin = 12.0,
                    tempMax = 13.0
                ),
                weather = weatherList,
                clouds = null,
                rain = RainModel(12.1, 12.2),
                snow = SnowModel(1.1,2.1),
                date = 1485789600,
                visibility = 11.1,
                wind = null
            )

            val weatherModelSecond = WeatherModel(
                id = 2641549,
                cityName = "Newtonhill",
                coords = Coordinates(-0.13, 51.51),
                mainConditions = Conditions(
                    temp = 280.32,
                    pressure = 1012.1,
                    humidity = 81.1,
                    tempMin = 12.0,
                    tempMax = 13.0
                ),
                weather = weatherList,
                clouds = null,
                rain = RainModel(12.1, 12.2),
                snow = SnowModel(1.1,2.1),
                date = 1521204600,
                visibility = 11.1,
                wind = null
            )

            var weatherModelList = ArrayList<WeatherModel>()
            weatherModelList.add(weatherModelFirst)
            weatherModelList.add(weatherModelSecond)
            var citiesModel = CitiesModel(
                message = "accurate",
                cod = 200,
                count = 2,
                list = weatherModelList
            )
            return citiesModel
        }
    }

}