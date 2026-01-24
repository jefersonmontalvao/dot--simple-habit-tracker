package com.example.dot__simple_habit_tracker.data.repository

import com.example.dot__simple_habit_tracker.data.local.datastore.AppUsageDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InAppReviewRepository @Inject constructor(
    private val appUsageDataStore: AppUsageDataStore
) {
    val hasRequestedReview: Flow<Boolean> = appUsageDataStore.hasRequestedReviewFlow
    val canRequestInAppReview: Flow<Boolean> =
        combine(
            flow = appUsageDataStore.hasEverReached3DayHabitCleanStreakFlow,
            flow2 = appUsageDataStore.hasEverOpenedAppWithActiveHabitsFlow,
            flow3 = hasRequestedReview
        ) { hasCleanStreak, hasActiveHabits, hasRequested ->
            hasCleanStreak && hasActiveHabits && !hasRequested
        }.distinctUntilChanged()

    suspend fun markHasRequestedReview() {
        appUsageDataStore.markHasRequestedReview()
    }
}