package com.example.dot__simple_habit_tracker.ui.navigation

import com.example.dot__simple_habit_tracker.ui.viewmodels.HabitsViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dot__simple_habit_tracker.ui.screens.InitHabitListScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.dot__simple_habit_tracker.ui.screens.InitAddHabitScreen
import com.example.dot__simple_habit_tracker.ui.screens.InitHabitDetailScreen
import com.example.dot__simple_habit_tracker.ui.viewmodels.SettingsViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val habitsViewModel: HabitsViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.HabitList.route
    ) {
        composable(Screen.HabitList.route) {
            InitHabitListScreen(
                habitsViewModel = habitsViewModel,
                settingsViewModel = settingsViewModel,
                navigateToAddHabit = {
                    navController.navigate(Screen.AddHabit.route)
                },
                navigateToHabitDetails = { habitId: String ->
                    navController.navigate(Screen.HabitDetail.createRoute(habitId))
                }
            )
        }

        composable(
            Screen.HabitDetail.route,
            arguments = listOf(
                navArgument("habitId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val habitId: String = backStackEntry.arguments?.getString("habitId")?: ""

            InitHabitDetailScreen(
                habitsViewModel = habitsViewModel,
                habitId = habitId,
                backAction = { navController.popBackStack() })
        }

        composable(Screen.AddHabit.route) {
            InitAddHabitScreen(
                habitsViewModel = habitsViewModel,
                backAction = { navController.popBackStack() }
            )
        }
    }
}