package com.example.dot__simple_habit_tracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dot__simple_habit_tracker.data.local.entities.HabitEntity
import com.example.dot__simple_habit_tracker.data.local.database.dao.HabitDao

@Database(entities = [HabitEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}