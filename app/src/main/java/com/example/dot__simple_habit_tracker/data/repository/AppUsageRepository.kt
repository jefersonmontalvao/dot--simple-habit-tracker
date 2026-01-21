package com.example.dot__simple_habit_tracker.data.repository

import com.example.dot__simple_habit_tracker.data.local.datastore.AppUsageDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUsageRepository @Inject constructor(
  private val appUsageDataStore: AppUsageDataStore
) {
    suspend fun markOpenedAppWithActiveHabits() {
        appUsageDataStore.markHasEverOpenedAppWithActiveHabits()
    }

    suspend fun markReached3DayHabitCleanStreak() {
        appUsageDataStore.markHasEverReached3DayHabitCleanStreak()
    }

}