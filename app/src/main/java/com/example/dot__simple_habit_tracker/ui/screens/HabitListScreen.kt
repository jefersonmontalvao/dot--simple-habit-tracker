package com.example.dot__simple_habit_tracker.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.dot__simple_habit_tracker.R
import com.example.dot__simple_habit_tracker.domain.models.Habit
import com.example.dot__simple_habit_tracker.ui.viewmodels.HabitsViewModel
import com.example.dot__simple_habit_tracker.ui.viewmodels.SettingsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate


@Composable
    
fun InitHabitListScreen(
    habitsViewModel: HabitsViewModel,
    settingsViewModel: SettingsViewModel,
    navigateToAddHabit: () -> Unit,
    navigateToHabitDetails: (String) -> Unit
) {
    var habitToBreakStreak by remember { mutableStateOf<Habit?>(null) }
    val habits: List<Habit> by habitsViewModel.habits.collectAsState(initial = emptyList())
    val darkModeEnabled: Boolean by settingsViewModel.darkMode.collectAsState(initial = false)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                HabitListHeader(
                    isDarkMode = darkModeEnabled,
                    toggleDarkMode = { settingsViewModel.toggleDarkMode(enable = it) }
                )

                HorizontalDivider(thickness = 1.dp)

                Spacer(Modifier.height(25.dp))

                HabitListScreenMotivationalCard()

                Spacer(Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    HabitList(
                        habitList = habits,
                        navigateToDetails = navigateToHabitDetails,
                        onFailureClick = { habitToBreakStreak = it }

                    )
                }

                HorizontalDivider(thickness = 1.dp)

                AddHabitButton(onAddClick = navigateToAddHabit)
            }
        }
    }

    if (habitToBreakStreak != null) {
        habitToBreakStreak?.let { habit: Habit ->
                ConfirmBreakStreakCard(
                    habit = habit,
                    onConfirm = {
                        val updatedHabit = habit.updateStreak()
                        habitsViewModel.updateHabit(updatedHabit)
                        habitToBreakStreak = null
                                },
                    onDismiss = { habitToBreakStreak = null }
                )
        }
    }
}

@Composable
private fun HabitListHeader(
    isDarkMode: Boolean,
    toggleDarkMode: (Boolean) -> Unit
    ) {
    var themeIconResource by remember {
        mutableIntStateOf(
            value = if (isDarkMode) {
                R.drawable.dark_ic_theme
            } else {
                R.drawable.light_ic_theme
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier.size(35.dp)
        )
        Text(
            text = stringResource(id = R.string.habit_screen_title),
            style = MaterialTheme.typography
                .headlineMedium.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 25.dp),
        )

        IconButton(
            onClick = {
                toggleDarkMode(!isDarkMode)
                themeIconResource = if (themeIconResource == R.drawable.light_ic_theme) {
                    R.drawable.dark_ic_theme
                } else {
                    R.drawable.light_ic_theme
                }
                toggleDarkMode(!isDarkMode)
            }
        ) {
            Icon(
                modifier = Modifier.size(35.dp),
                painter = painterResource(themeIconResource),
                contentDescription = stringResource(R.string.action_toggle_theme)
            )
        }
    }
}

@Composable
private fun HabitListScreenMotivationalCard() {
    Surface (
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 10.dp,
        color = MaterialTheme.colorScheme.surface
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
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun HabitList(
    habitList: List<Habit>,
    navigateToDetails: (String) -> Unit,
    onFailureClick: (Habit) -> Unit
) {
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
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = if (habitList.isEmpty()) TextAlign.Center else TextAlign.Start
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(habitList) { index, habit: Habit ->
                HabitItem(
                    habit = habit,
                    isLastHabitItem = habitList.lastIndex == index,
                    navigateToDetails = navigateToDetails,
                    onFailureClick = onFailureClick
                )
            }
        }
        if (habitList.isNotEmpty()) {
            val areAllHabitsNew = habitList.all { habit ->
                habit.daysSinceCreation() == 0.toLong()
            }

            if (areAllHabitsNew)
                BreakStreakTip()
        }

    }
}

@Composable
private fun HabitItem(
    habit: Habit,
    isLastHabitItem: Boolean,
    navigateToDetails: (String) -> Unit,
    onFailureClick: (Habit) -> Unit
) {
    val scope = rememberCoroutineScope()
    var showFailureMarkedAdvice by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { navigateToDetails(habit.id) },
        shape = RoundedCornerShape(10.dp),
        color = Color.Transparent
    ) {
        val textColor: Color = when {
            habit.daysSinceCreation().toInt() == 0 -> Color.Red
            habit.daysSinceCreation().toInt() in 1..7 -> MaterialTheme.colorScheme.onSurface
            habit.daysSinceCreation().toInt() in 8..<21 -> MaterialTheme.colorScheme.secondary
            else -> Color(0xFF4CAF50)
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(start = 7.dp)
                    .weight(1f)
            )

            Spacer(Modifier.size(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.days_label, habit.daysSinceCreation()),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    ),
                    modifier = Modifier
                        .padding(end = 5.dp)
                )

                IconButton(
                    onClick = {
                        if (habit.lastBreakStreakDate() == null || habit.lastBreakStreakDate()!!.toLocalDate() != LocalDate.now()) {
                            onFailureClick(habit)
                        } else {
                            scope.launch {
                                showFailureMarkedAdvice = true
                                delay(1700)
                                showFailureMarkedAdvice = false
                            }

                        }
                    }
                ) {
                    Icon(imageVector = Icons.Outlined.Close,
                        contentDescription = stringResource(R.string.mark_failure_content_description))
                }
            }
        }
    }

    if (showFailureMarkedAdvice) {
        Box(Modifier.offset(y = (-15).dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp)
                    .height(18.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = stringResource(id = R.string.failure_already_recorded_info),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    } else {
        Spacer(Modifier.size(18.dp))
    }
    if (!isLastHabitItem) {
        HorizontalDivider(thickness = 1.dp, modifier = Modifier.padding(bottom = 10.dp))
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

@Composable
private fun ConfirmBreakStreakCard(
    habit: Habit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {},
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.confirm_break_streak_card_impact_title),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(
                                stringResource(
                                    R.string.confirm_break_streak_card_comfort
                                ).split("\n")[0]
                            )
                        }

                        append("\n")

                        append(
                            stringResource(
                                R.string.confirm_break_streak_card_comfort
                            ).split("%1\$s")[0].split("\n")[1]
                        )

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold
                            )
                        ) {
                            append(habit.name.lowercase())
                        }

                        append(
                            stringResource(
                                R.string.confirm_break_streak_card_comfort
                            ).split("%1\$s")[1]
                        )
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.action_confirm_break_streak_card_cancel),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Button(
                        onClick = onConfirm,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.action_confirm_break_streak_card_fail),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BreakStreakTip() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = stringResource(R.string.tip_break_streak_text),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}
