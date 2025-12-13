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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dot__simple_habit_tracker.R
import com.example.dot__simple_habit_tracker.domain.models.Habit
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

                Spacer(Modifier.height(25.dp))
                HabitListScreenMotivationalCard()

                Box(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    HabitList(
                        habitList = habits,
                        navigateToDetails = navigateToHabitDetails
                    )
                }

                HorizontalDivider(thickness = 1.dp)

                AddHabitButton(onAddClick = navigateToAddHabit)
            }
        }
    }
}

@Composable
private fun HabitListHeader() {
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
private fun HabitListScreenMotivationalCard() {
    Surface (
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 10.dp,
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = getRandomMotivationalPhrase(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 30.sp,
                    fontFamily = FontFamily.Serif
                )
            )
        }
    }
}

@Composable
private fun HabitList(habitList: List<Habit>, navigateToDetails: (String) -> Unit) {
    Column {
        Text(
            text = when {
                habitList.size == 1 -> stringResource(
                    R.string.habits_quantity_nonplural_label,
                    habitList.size
                )
                habitList.size > 1 -> stringResource(
                    R.string.habits_quantity_plural_label,
                    habitList.size
                )
                else -> stringResource(
                    R.string.empty_habits_quantity_label
                )
            },
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier
                .padding(vertical = 10.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(habitList) { index, habit: Habit ->
                HabitItem(
                    habit = habit,
                    isLastHabitItem = habitList.lastIndex == index,
                    navigateToDetails = navigateToDetails
                )
            }
        }
    }
}

@Composable
private fun HabitItem(habit: Habit, isLastHabitItem: Boolean, navigateToDetails: (String) -> Unit) {
    Surface(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(7.dp),
        onClick = {
            navigateToDetails(habit.id)
                  },
    ) {
        val textColor: Color = when {
            habit.daysSinceCreation().toInt() == 0 -> Color.Red
            habit.daysSinceCreation().toInt() in 1..7 -> MaterialTheme.colorScheme.onSurface
            habit.daysSinceCreation().toInt() in 8..<21 -> MaterialTheme.colorScheme.secondary
            else -> Color(0xFF4CAF50)
        }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .padding(start = 7.dp)
                )

                Text(
                    text = stringResource(id = R.string.days_label, habit.daysSinceCreation()),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    ),
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
private fun AddHabitButton(onAddClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .height(55.dp)
            .fillMaxWidth(),
        onClick = { onAddClick() },
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 1.dp,
        color = Color(0xFF4CAF50)
    ) {
        Box(
            modifier = Modifier
                .padding(start = 7.dp),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.action_add_habit),
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(id = R.string.action_add_habit),
                    style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
                )
            }
        }
    }
}
