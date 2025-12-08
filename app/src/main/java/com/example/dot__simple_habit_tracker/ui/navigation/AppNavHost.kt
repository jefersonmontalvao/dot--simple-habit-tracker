package com.example.dot__simple_habit_tracker.ui.navigation

import com.example.dot__simple_habit_tracker.ui.viewmodels.HabitsViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dot__simple_habit_tracker.ui.screens.InitHabitListScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dot__simple_habit_tracker.ui.screens.InitHabitDetailScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HabitList.route
    ) {
        composable(Screen.HabitList.route) {
            val viewModel: HabitsViewModel = hiltViewModel()

            InitHabitListScreen(
                viewModel,
                onAddClick = {
                    navController.navigate(Screen.AddHabit.route)
                },
                onHabitClick = { habitId ->
                    navController.navigate(Screen.HabitDetail.createRoute(habitId))
                }
            )
        }

        composable(Screen.HabitDetail.route) { backStackEnty ->
            val viewModel: HabitsViewModel = hiltViewModel()
            val habitId: String = backStackEnty.arguments?.getString("habitId")?: ""

            InitHabitDetailScreen(
                viewModel = viewModel,
                habitId = habitId,
                backAction = { navController.popBackStack() })
        }

        composable(Screen.AddHabit.route) {
            // TODO
        }
    }
}