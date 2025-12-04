package com.example.dot__simple_habit_tracker.di

import android.content.Context
import androidx.room.Room
import com.example.dot__simple_habit_tracker.data.local.database.AppDatabase
import com.example.dot__simple_habit_tracker.data.local.database.dao.HabitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "habit.db")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideHabitDao(db: AppDatabase): HabitDao = db.habitDao()
}