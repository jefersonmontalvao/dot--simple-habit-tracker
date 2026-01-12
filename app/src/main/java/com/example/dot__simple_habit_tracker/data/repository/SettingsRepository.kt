package com.example.dot__simple_habit_tracker.data.repository

import com.example.dot__simple_habit_tracker.data.local.datastore.SettingsDataStore
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) {
    val darkMode = settingsDataStore.darkModeFlow

    suspend fun setDarkMode(enabled: Boolean) {
        settingsDataStore.setDarkMode(enabled)
    }
}