package com.example.dot__simple_habit_tracker.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dot__simple_habit_tracker.R
import com.example.dot__simple_habit_tracker.domain.models.Habit
import com.example.dot__simple_habit_tracker.ui.theme.Typography
import com.example.dot__simple_habit_tracker.ui.viewmodels.HabitsViewModel

@Composable
fun InitHabitDetailScreen(
    viewModel: HabitsViewModel,
    habitId: String,
    backAction: () -> Unit
    ) {
    val habits: List<Habit> by viewModel.habits.collectAsState(initial = emptyList())
    val habit: Habit? = habits.find { it.id == habitId }

    if (habit == null) {
        return
    }

    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {

                HabitDetailHeader(
                    habit = habit,
                    backArrowAction = backAction
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    HabitDetailCounter(habit = habit)
                }

                HabitDetailProgress(habit = habit)

                HabitDetailDeleteButton(habit = habit, viewModel = viewModel, backAction = backAction)
            }
        }
    }
}

@Composable
private fun HabitDetailHeader(habit: Habit, backArrowAction: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(vertical = 25.dp),
        contentAlignment = Alignment.Center
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                shape = RoundedCornerShape(5.dp),
                onClick = { backArrowAction() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier
                        .size(35.dp)
                )
            }

            Text(
                text = habit.name,
                style = MaterialTheme.typography
                    .headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.size(35.dp))
        }

    }

    HorizontalDivider(
        thickness = 1.dp,
    )
}

@Composable
private fun HabitDetailCounter(habit: Habit) {

    Column {
        Text(
            text = stringResource(id = R.string.active_habit_for),
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp,
                letterSpacing = 0.5.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = habit.daysSinceCreation().toString(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 140.sp,
                letterSpacing = 0.5.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = stringResource(R.string._days),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp,
                letterSpacing = 0.5.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun HabitDetailProgress(habit: Habit) {
    HorizontalDivider(
        thickness = 1.dp
    )

    Column (
        modifier = Modifier
            .padding(vertical = 25.dp)
    ) {
        Text(
            stringResource(R.string.progress),
            style = Typography.bodyLarge.copy(
                color = Color(0xFF9E9E9E),
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .padding(bottom = 10.dp)
        )
        Text(
            text = stringResource(
                id = R.string.init_date_details,
                habit.creationDate.dayOfMonth,
                habit.creationDate.monthValue.let { month ->
                    findMonthTranslation(month = month)
                },
                habit.creationDate.year
                ),
            style = Typography.bodyLarge.copy(color = Color(0xFF9E9E9E))
        )

        Text(
            text = stringResource(
                id = R.string.total_days_details,
                habit.daysSinceCreation()
            ),
            style = Typography.bodyLarge.copy(color = Color(0xFF9E9E9E))
        )

        Text(
            text = stringResource(id = R.string.max_streak_details, habit.maxStreak()),
            style = Typography.bodyLarge.copy(color = Color(0xFF9E9E9E))
        )

        Text(
            text = stringResource(
                id = R.string.last_failure_date_details,
                habit.lastBreakStreakDate.dayOfMonth,
                habit.lastBreakStreakDate.monthValue.let { month ->
                    findMonthTranslation(month = month)
                },
                habit.lastBreakStreakDate.year
            ),
            style = Typography.bodyLarge.copy(color = Color(0xFF9E9E9E))
        )
    }

    HorizontalDivider(
        thickness = 1.dp
    )
}

@Composable
private fun HabitDetailDeleteButton(habit: Habit, viewModel: HabitsViewModel, backAction: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .height(55.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            backAction()
            viewModel.delHabit(habit = habit)
        },
        tonalElevation = 1.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.delete_habit),
                color = MaterialTheme.colorScheme.error,
                style = Typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun findMonthTranslation(month: Int): String {
    val monthNameResourceId = when (month) {
        1 -> R.string.month_january
        2 -> R.string.month_february
        3 -> R.string.month_march
        4 -> R.string.month_april
        5 -> R.string.month_may
        6 -> R.string.month_june
        7 -> R.string.month_july
        8 -> R.string.month_august
        9 -> R.string.month_september
        10 -> R.string.month_october
        11 -> R.string.month_november
        12 -> R.string.month_december
        else -> 0
    }

    return stringResource(id = monthNameResourceId)
}
