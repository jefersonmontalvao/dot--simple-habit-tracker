package com.example.dot__simple_habit_tracker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dot__simple_habit_tracker.data.repository.HabitRepository
import com.example.dot__simple_habit_tracker.domain.model.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitsViewModel @Inject constructor(
    private val repository: HabitRepository
) : ViewModel() {

    val habits: Flow<List<Habit>> = repository.getHabits()

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.addHabit(habit)
        }
    }

    fun delHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }
}