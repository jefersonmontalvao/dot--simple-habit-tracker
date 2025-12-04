package com.example.dot__simple_habit_tracker.data.local.database

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Converters {
    private val fmt = DateTimeFormatter.ISO_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun fromString(value: String?): LocalDateTime? =
        value?.let { LocalDateTime.parse(it, fmt) }

    @TypeConverter
    @JvmStatic
    fun toString(date: LocalDateTime?): String? =
        date?.format(fmt)
}