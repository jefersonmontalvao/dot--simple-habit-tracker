package com.example.dot__simple_habit_tracker.domain.models

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

data class Habit(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val creationDate: LocalDateTime = LocalDateTime.now(),
) {
    fun daysSinceCreation(): Long =
        ChronoUnit.DAYS.between(creationDate, LocalDateTime.now()
    )
}