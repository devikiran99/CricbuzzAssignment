package com.devikiran.cricbuzz.ui.screens.weather_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devikiran.cricbuzz.data.local.module.ForecastEntity
import com.devikiran.cricbuzz.ui.screens.WeatherItem

@Composable
fun WeatherListScreen(viewModel: WeatherListViewModel) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSavedLocations()
    }

    if (state.locations.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No saved locations!!. Please search for specified location weather data..",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            item {
                Text(
                    text = "Weather List",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 8.dp, bottom = 24.dp)
                )
            }

            state.locations.forEach { (city, forecasts) ->
                item {
                    WeatherListCard(
                        city = city,
                        forecasts = forecasts,
                        refreshState =
                            state.refreshStates[city]
                                ?: WeatherRefreshState.Idle,
                        onRefresh = {
                            viewModel.refreshLocation(city)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherListCard(
    city: String,
    forecasts: List<ForecastEntity>,
    refreshState: WeatherRefreshState,
    onRefresh: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            Color.DarkGray.copy(alpha = 0.2f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = city,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                when (refreshState) {
                    WeatherRefreshState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }

                    else -> {
                        IconButton(
                            modifier = Modifier.size(40.dp),
                            onClick = onRefresh
                        ) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(2.dp),
                color = Color.White.copy(alpha = 0.5f)
            )

            forecasts.forEach { forecast ->
                WeatherItem(forecast)
            }
        }
    }
}

