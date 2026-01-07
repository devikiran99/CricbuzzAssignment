package com.devikiran.cricbuzz.ui.screens.weather_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devikiran.cricbuzz.data.WeatherRepository
import com.devikiran.cricbuzz.utils.WeatherResult
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow(WeatherListUiState())

    val uiState: StateFlow<WeatherListUiState> =
        _uiState.asStateFlow()

    fun loadSavedLocations() {
        viewModelScope.launch {
            val locations = repository.getAllSavedLocations()
            _uiState.update {
                it.copy(locations = locations)
            }
        }
    }

    fun refreshLocation(city: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    refreshStates = state.refreshStates +
                            (city to WeatherRefreshState.Loading)
                )
            }

            val result = repository.fetchWeather(city)

            _uiState.update { state ->
                state.copy(
                    refreshStates = state.refreshStates +
                            (city to when (result) {
                                is WeatherResult.Success ->
                                    WeatherRefreshState.Idle
                                is WeatherResult.Error ->
                                    WeatherRefreshState.Error(result.message)
                                else -> WeatherRefreshState.Idle
                            })
                )
            }

            loadSavedLocations()
        }
    }
}
