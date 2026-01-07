package com.devikiran.cricbuzz.utils

sealed class NavRoute(val route: String, val title: String) {
    object Search : NavRoute("search", "Location Search")
    object Locations : NavRoute("locations", "Weather List")
}
