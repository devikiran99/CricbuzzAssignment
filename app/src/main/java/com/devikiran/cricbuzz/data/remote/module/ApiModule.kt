package com.devikiran.cricbuzz.data.remote.module

data class WeatherResponse(
    val city: City,
    val list: List<ForecastItem>
)

data class City(val name: String)

data class ForecastItem(
    val dt_txt: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(val temp: Double)

data class Weather(
    val description: String,
    val icon: String
)
