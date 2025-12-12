package com.example.dot__simple_habit_tracker.ui.models

import com.example.dot__simple_habit_tracker.domain.models.Habit

data class HabitSuggestion(
    val habit: Habit,
    val impactTagLine: String = "",
    val description: String
)
