package com.devikiran.cricbuzz.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devikiran.cricbuzz.ui.screens.WeatherItem
import com.devikiran.cricbuzz.utils.WeatherResult

@Composable
fun WeatherSearchScreen(
    viewModel: WeatherSearchViewModel
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        when (val result = state.weatherResult) {

            WeatherResult.Idle -> {
                MainContent(
                    state = state,
                    viewModel = viewModel
                ) {
                    CenteredHint(message = "Enter a city to see weather forecast")
                }
            }

            WeatherResult.Loading -> {
                MainContent(
                    state = state,
                    viewModel = viewModel
                ){
                    LoadingOverlay()
                }
            }

            is WeatherResult.Success -> {
                MainContent(
                    state = state,
                    viewModel = viewModel
                ) {
                    LazyColumn {
                        items(result.data) {
                            WeatherItem(it)
                        }
                    }
                }
            }

            is WeatherResult.Error -> {
                MainContent(
                    state = state,
                    viewModel = viewModel
                ) {
                    CenteredHint(
                        message = result.message,
                        color = Color.Red

                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(
    state: WeatherUiState,
    viewModel: WeatherSearchViewModel,
    content: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Search for 3 days Weather data",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(2.dp),
            color = Color.White.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.city,
            onValueChange = viewModel::onCityChanged,
            label = { Text("Enter City name") },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = viewModel::fetchWeather,
            enabled = state.isButtonEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        content?.invoke()
    }
}

@Composable
fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun CenteredHint(
    message: String,
    color: Color = Color.LightGray.copy(alpha = 0.3f)
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 20.dp, vertical = 12.dp),
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}



