package com.example.dot__simple_habit_tracker.data.repository

import com.example.dot__simple_habit_tracker.data.local.database.dao.HabitDao
import com.example.dot__simple_habit_tracker.data.mappers.toDomain
import com.example.dot__simple_habit_tracker.data.mappers.toEntity
import com.example.dot__simple_habit_tracker.domain.models.Habit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(
    private val dao: HabitDao
) {
    fun getHabits(): Flow<List<Habit>> {
        return dao.getAll().map { flowsList ->
            flowsList.map { it.toDomain() }
        }
    }

    suspend fun addHabit(habit: Habit) {
        dao.insert(habit.toEntity())
    }

    suspend fun deleteHabit(habit: Habit) {
        dao.delete(habit.toEntity())
    }

    suspend fun getHabitById(id: String): Habit? = dao.getById(id)?.toDomain()
}