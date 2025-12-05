package com.example.dot__simple_habit_tracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dot__simple_habit_tracker.R
import com.example.dot__simple_habit_tracker.domain.model.Habit
import com.example.dot__simple_habit_tracker.ui.viewmodels.HabitsViewModel


@Composable
fun InitHabitListScreen(viewModel: HabitsViewModel) {
    val habits: List<Habit> by viewModel.habits.collectAsState(initial = emptyList())

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = stringResource(id = R.string.habit_screen_title),
                style = MaterialTheme.typography
                    .headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 8.dp)
            )

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            )


            HabitList(modifier = Modifier.weight(1f), habitList = habits)

            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))

            AddHabitButton(viewModel = viewModel)
        }
    }
}

@Composable
fun HabitList(modifier: Modifier, habitList: List<Habit>) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(habitList) { habit: Habit ->
            HabitItem(
                habit = habit,
                isLastHabitItem = habitList.lastIndexOf(habit) == habitList.size - 1)
        }
    }
}

@Composable
fun HabitItem(habit: Habit, isLastHabitItem: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = habit.name,
            style = MaterialTheme.typography.bodyLarge
            )

        Text(
            text = stringResource(id = R.string.days_label, habit.daysSinceCreation()),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )


    }
    if (!isLastHabitItem) {
        HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
    }
}

@Composable
fun AddHabitButton(viewModel: HabitsViewModel) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 20.dp )
            .height(45.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        onClick = {
        /*TODO: Add habit action
        *  from viewModel*/
        },
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.action_add_habit)
                )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.action_add_habit),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}