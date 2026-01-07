package com.devikiran.cricbuzz.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devikiran.cricbuzz.ui.screens.search.WeatherSearchScreen
import com.devikiran.cricbuzz.ui.screens.search.WeatherSearchViewModel
import com.devikiran.cricbuzz.ui.screens.weather_list.WeatherListScreen
import com.devikiran.cricbuzz.ui.screens.weather_list.WeatherListViewModel
import com.devikiran.cricbuzz.utils.NavRoute

@Composable
fun MainScreen() {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = NavRoute.Search.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(NavRoute.Search.route) {
                val viewModel: WeatherSearchViewModel = hiltViewModel<WeatherSearchViewModel>()
                WeatherSearchScreen(viewModel)
            }
            composable(NavRoute.Locations.route) {
                val viewModel: WeatherListViewModel = hiltViewModel<WeatherListViewModel>()
                WeatherListScreen(viewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val items = listOf(
        NavRoute.Search,
        NavRoute.Locations
    )

    NavigationBar {
        val currentRoute =
            navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            val icon = if(item.route == "search"){ Icons.Default.Search }else{ Icons.Default.Cloud }
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(item.title) },
                icon = { Icon(icon, contentDescription = null) }
            )
        }
    }
}

