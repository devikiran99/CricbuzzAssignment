package com.devikiran.cricbuzz.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.devikiran.cricbuzz.ui.screens.MainScreen
import com.devikiran.cricbuzz.ui.theme.CricbuzzTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CricbuzzTheme {
                MainScreen()
            }
        }
    }
}