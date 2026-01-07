package com.devikiran.cricbuzz.ui.screens.weather_list

sealed class WeatherRefreshState {
    object Idle : WeatherRefreshState()
    object Loading : WeatherRefreshState()
    data class Error(val message: String) : WeatherRefreshState()
}
