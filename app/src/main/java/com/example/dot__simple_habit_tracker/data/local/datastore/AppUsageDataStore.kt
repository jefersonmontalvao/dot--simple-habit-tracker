package com.example.dot__simple_habit_tracker.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppUsageDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val HAS_EVER_REACHED_3_DAY_CLEAN_STREAK =
        booleanPreferencesKey("has_ever_reached_3_day_clean_streak")
    private val HAS_EVER_OPENED_APP_WITH_ACTIVE_HABITS =
        booleanPreferencesKey("has_ever_opened_app_with_active_habits")
    private val HAS_REQUESTED_REVIEW = booleanPreferencesKey("has_requested_review")


    val hasEverReached3DayHabitCleanStreakFlow: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[HAS_EVER_REACHED_3_DAY_CLEAN_STREAK] ?: false
        }

    val hasEverOpenedAppWithActiveHabitsFlow: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[HAS_EVER_OPENED_APP_WITH_ACTIVE_HABITS] ?: false
        }

    val hasRequestedReviewFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[HAS_REQUESTED_REVIEW] ?: false
    }


    suspend fun markHasEverReached3DayHabitCleanStreak() {
        dataStore.edit { preferences ->
            preferences[HAS_EVER_REACHED_3_DAY_CLEAN_STREAK] = true
        }
    }

    suspend fun markHasEverOpenedAppWithActiveHabits() {
        dataStore.edit { preferences ->
            preferences[HAS_EVER_OPENED_APP_WITH_ACTIVE_HABITS] = true
        }
    }

    suspend fun markHasRequestedReview() {
        dataStore.edit { preferences ->
            preferences[HAS_REQUESTED_REVIEW] = true
        }
    }
}