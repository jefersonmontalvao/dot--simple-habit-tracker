package com.example.dot__simple_habit_tracker.domain.models

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

data class Habit(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    private var _lastBreakStreakDate: LocalDateTime = creationDate,
    private var _maxStreak: Long = 0
) {
    fun daysSinceCreation(): Long = ChronoUnit.DAYS.between(
        creationDate,
        LocalDateTime.now()
    )

    fun lastBreakStreakDate(): LocalDateTime? =
        if (_lastBreakStreakDate == creationDate) null else _lastBreakStreakDate

    fun maxStreak(): Long {
        val currentStreak = ChronoUnit.DAYS.between(
            _lastBreakStreakDate,
            LocalDateTime.now()
        )

        _maxStreak = if (currentStreak > _maxStreak) currentStreak else _maxStreak
        return _maxStreak
    }

    fun updateStreak(): Habit {
        val newBreakTime: LocalDateTime = LocalDateTime.now()

        return copy(_lastBreakStreakDate = newBreakTime)
    }
}