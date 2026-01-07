package com.devikiran.cricbuzz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devikiran.cricbuzz.data.local.dao.ForecastDao
import com.devikiran.cricbuzz.data.local.module.ForecastEntity

@Database(entities = [ForecastEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun forecastDao(): ForecastDao
}