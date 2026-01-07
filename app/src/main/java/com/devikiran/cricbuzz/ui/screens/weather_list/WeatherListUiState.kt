package com.devikiran.cricbuzz.ui.screens.weather_list

import com.devikiran.cricbuzz.data.local.module.ForecastEntity

data class WeatherListUiState(
    val locations: Map<String, List<ForecastEntity>> = emptyMap(),
    val refreshStates: Map<String, WeatherRefreshState> = emptyMap()
)
