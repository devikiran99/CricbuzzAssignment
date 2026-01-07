package com.devikiran.cricbuzz.utils

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun String.iconUrl(): String {
        return "https://openweathermap.org/img/wn/${this}@2x.png"
    }
}