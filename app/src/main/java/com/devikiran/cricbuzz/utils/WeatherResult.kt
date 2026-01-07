package com.devikiran.cricbuzz.utils

import com.devikiran.cricbuzz.data.local.module.ForecastEntity

sealed class WeatherResult {
    object Idle : WeatherResult()
    object Loading : WeatherResult()
    data class Success(val data: List<ForecastEntity>) : WeatherResult()
    data class Error(val message: String) : WeatherResult()
}
