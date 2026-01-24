package com.example.dot__simple_habit_tracker.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dot__simple_habit_tracker.ui.navigation.AppNavHost
import com.example.dot__simple_habit_tracker.ui.theme.AppTheme
import com.example.dot__simple_habit_tracker.ui.viewmodels.SettingsViewModel

@Composable
fun AppRoot(
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val isDarkMode by settingsViewModel.darkMode.collectAsState()

    AppTheme(darkTheme = isDarkMode) {
        AppNavHost()
    }
}