package com.devikiran.cricbuzz.data.local.module

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val date: String,
    val temperature: Double,
    val description: String,
    val icon: String
)
