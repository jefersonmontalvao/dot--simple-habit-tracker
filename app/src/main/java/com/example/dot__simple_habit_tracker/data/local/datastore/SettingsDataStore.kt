package com.example.dot__simple_habit_tracker.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val DARK_MODE = booleanPreferencesKey("dark_mode")

    val darkModeFlow: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[DARK_MODE] ?: false
        }

    suspend fun setDarkMode(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = value
        }
    }
}