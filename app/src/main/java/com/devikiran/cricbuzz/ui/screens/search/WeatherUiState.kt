package com.devikiran.cricbuzz.ui.screens.search

import com.devikiran.cricbuzz.utils.WeatherResult

data class WeatherUiState(
    val city: String = "",
    val isButtonEnabled: Boolean = false,
    val weatherResult: WeatherResult = WeatherResult.Idle,
    val errorMessage: String? = null
)