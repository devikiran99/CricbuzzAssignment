package com.devikiran.cricbuzz.data

import android.content.Context
import com.devikiran.cricbuzz.BuildConfig
import com.devikiran.cricbuzz.data.local.AppDatabase
import com.devikiran.cricbuzz.data.local.module.ForecastEntity
import com.devikiran.cricbuzz.data.remote.WeatherApiService
import com.devikiran.cricbuzz.utils.NetworkUtils
import com.devikiran.cricbuzz.utils.WeatherResult

class WeatherRepository(
    private val context: Context,
    private val api: WeatherApiService,
    db: AppDatabase
) {
    private val dao = db.forecastDao()

    suspend fun fetchWeather(city: String): WeatherResult {

        return try {
            if (NetworkUtils.isNetworkAvailable(context)) {

                val response = api.getForecast(city, BuildConfig.API_KEY)

                val dailyForecast = response.list
                    .filter { it.dt_txt.contains("12:00:00") }
                    .take(3)

                val entities = dailyForecast.map {
                    ForecastEntity(
                        city = city,
                        date = it.dt_txt.split(" ")[0],
                        temperature = it.main.temp,
                        description = it.weather[0].description,
                        icon = it.weather[0].icon
                    )
                }

                dao.deleteCity(city)
                dao.insertAll(entities)

                WeatherResult.Success(entities)
            } else {
                val cachedData = dao.getForecast(city)
                if (cachedData.isNotEmpty()) {
                    WeatherResult.Success(cachedData)
                } else {
                    WeatherResult.Error("No internet and no cached data available")
                }
            }

        } catch (e: Exception) {
            WeatherResult.Error("Something went wrong: ${e.localizedMessage}")
        }
    }

    suspend fun getAllSavedLocations(): Map<String, List<ForecastEntity>> {
        val cities = dao.getAllCities()
        return cities.associateWith { city ->
            dao.getForecastByCity(city)
        }
    }
}