package com.example.dot__simple_habit_tracker.data.mappers

import com.example.dot__simple_habit_tracker.data.local.entities.HabitEntity
import com.example.dot__simple_habit_tracker.domain.models.Habit

fun HabitEntity.toDomain(): Habit =
    Habit(
        id = id,
        name = name,
        creationDate = creationDate,
        lastBreakStreakDate = lastBreakStreakDate,
        _maxStreak = maxStreak
    )

fun Habit.toEntity(): HabitEntity =
    HabitEntity(
        id = id,
        name = name,
        creationDate = creationDate,
        lastBreakStreakDate = lastBreakStreakDate,
        maxStreak = maxStreak()
    )
