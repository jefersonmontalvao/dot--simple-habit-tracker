package com.example.dot__simple_habit_tracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
    
fun InitHabitListScreen(
    viewModel: HabitsViewModel,
    navigateToAddHabit: () -> Unit,
    navigateToHabitDetails: (String) -> Unit
) {
    val habits: List<Habit> by viewModel.habits.collectAsState(initial = emptyList())

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                HabitListHeader()

                HorizontalDivider(thickness = 1.dp)

                Box(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    HabitList(habitList = habits, navigateToDetails = navigateToHabitDetails)
                }

                HorizontalDivider(thickness = 1.dp)

                AddHabitButton(onAddClick = navigateToAddHabit)
            }
        }
    }
}

@Composable
fun HabitListHeader() {
    Text(
        text = stringResource(id = R.string.habit_screen_title),
        style = MaterialTheme.typography
            .headlineMedium.copy(fontWeight = FontWeight.SemiBold),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp),
        )
}

@Composable
fun HabitList(habitList: List<Habit>, navigateToDetails: (String) -> Unit) {
    Column {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(habitList) { habit: Habit ->
                HabitItem(
                    habit = habit,
                    isLastHabitItem = habitList.lastIndexOf(habit) == habitList.size - 1,
                    navigateToDetails = navigateToDetails
                )
            }
        }
    }
}

@Composable
fun HabitItem(habit: Habit, isLastHabitItem: Boolean, navigateToDetails: (String) -> Unit) {
    Surface(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(7.dp),
        onClick = {
            navigateToDetails(habit.id)
                  },
    ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(start = 7.dp)
                )

                Text(
                    text = stringResource(id = R.string.days_label, habit.daysSinceCreation()),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(end = 7.dp)
                )
            }
    }

    if (!isLastHabitItem) {
        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))
    }
}

@Composable
fun AddHabitButton(onAddClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .height(55.dp)
            .fillMaxWidth(),
        onClick = { onAddClick() },
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 1.dp
    ) {
        Box(
            modifier = Modifier
                .padding(start = 7.dp),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.action_add_habit)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(id = R.string.action_add_habit),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
