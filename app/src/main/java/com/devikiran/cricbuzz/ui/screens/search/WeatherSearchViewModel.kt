package com.devikiran.cricbuzz.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devikiran.cricbuzz.data.WeatherRepository
import com.devikiran.cricbuzz.utils.WeatherResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun onCityChanged(city: String) {
        _uiState.value = _uiState.value.copy(
            city = city,
            isButtonEnabled = city.isNotBlank()
        )
    }

    fun fetchWeather() {
        val city = _uiState.value.city

        if (city.isBlank()) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                weatherResult = WeatherResult.Loading,
                errorMessage = null
            )

            val result = repository.fetchWeather(city)

            _uiState.value = _uiState.value.copy(
                weatherResult = result,
                errorMessage = if (result is WeatherResult.Error) result.message else null
            )
        }
    }
}