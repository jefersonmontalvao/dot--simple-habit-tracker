package com.example.dot__simple_habit_tracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey val id: String,
    val name: String,
    val creationDate: LocalDateTime,
    var lastBreakStreakDate: LocalDateTime,
    var maxStreak: Long
)