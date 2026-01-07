package com.devikiran.cricbuzz.di

import android.content.Context
import androidx.room.Room
import com.devikiran.cricbuzz.data.WeatherRepository
import com.devikiran.cricbuzz.data.local.AppDatabase
import com.devikiran.cricbuzz.data.remote.WeatherApiService
import com.devikiran.cricbuzz.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "weather_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesWeatherApiService(): WeatherApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesWeatherRepository(
        @ApplicationContext context: Context,
        apiService: WeatherApiService,
        database: AppDatabase
    ): WeatherRepository = WeatherRepository(context, apiService, database)
}