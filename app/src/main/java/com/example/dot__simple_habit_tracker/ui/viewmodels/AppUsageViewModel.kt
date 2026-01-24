package com.example.dot__simple_habit_tracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dot__simple_habit_tracker.data.repository.AppUsageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppUsageViewModel @Inject constructor(
    private val appUsageRepository: AppUsageRepository
) : ViewModel() {
    fun markOpenedAppWithActiveHabits() {
        viewModelScope.launch { appUsageRepository.markOpenedAppWithActiveHabits() }
    }

    fun markReached3DayHabitCleanStreak() {
        viewModelScope.launch { appUsageRepository.markReached3DayHabitCleanStreak() }
    }
}