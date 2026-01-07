package com.devikiran.cricbuzz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devikiran.cricbuzz.data.local.module.ForecastEntity

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast WHERE city = :city")
    suspend fun getForecast(city: String): List<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(forecasts: List<ForecastEntity>)

    @Query("DELETE FROM forecast WHERE city = :city")
    suspend fun deleteCity(city: String)

    @Query("SELECT DISTINCT city FROM forecast")
    suspend fun getAllCities(): List<String>

    @Query("SELECT * FROM forecast WHERE city = :city")
    suspend fun getForecastByCity(city: String): List<ForecastEntity>

}