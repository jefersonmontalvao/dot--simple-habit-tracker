package com.example.dot__simple_habit_tracker.ui.navigation

sealed class Screen(val route: String) {
    object HabitList : Screen("habit_list")

    object HabitDetail : Screen("habit_detail/{habitId}") {
        fun createRoute(habitId: String): String = "habit_detail/$habitId"
    }

    object AddHabit : Screen("add_habit")
}
