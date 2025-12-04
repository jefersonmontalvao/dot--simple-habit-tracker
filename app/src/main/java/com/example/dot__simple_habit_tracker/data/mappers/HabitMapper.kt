package com.example.dot__simple_habit_tracker.data.mappers

import com.example.dot__simple_habit_tracker.data.local.entities.HabitEntity
import com.example.dot__simple_habit_tracker.domain.models.Habit
import java.time.LocalDateTime

fun HabitEntity.toDomain(): Habit =
    Habit(
        id = id,
        name = name,
        creationDate = LocalDateTime.parse(creationDateIso)
    )

fun Habit.toEntity(): HabitEntity =
    HabitEntity(
        id = id,
        name = name,
        creationDateIso = creationDate.toString()
    )
