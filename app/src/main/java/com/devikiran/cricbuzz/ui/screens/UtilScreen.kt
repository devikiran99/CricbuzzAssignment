package com.devikiran.cricbuzz.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.devikiran.cricbuzz.data.local.module.ForecastEntity
import com.devikiran.cricbuzz.utils.Constants.iconUrl

@Composable
fun WeatherItem(item: ForecastEntity) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = item.icon.iconUrl(),
                contentDescription = item.description,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Date: ${item.date}")
                Text(text = "Temp: ${item.temperature}°C")
                Text(item.description.replaceFirstChar { it.uppercase() })
            }
        }
    }
}