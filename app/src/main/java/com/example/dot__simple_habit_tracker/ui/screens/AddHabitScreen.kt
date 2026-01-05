package com.example.dot__simple_habit_tracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dot__simple_habit_tracker.R
import com.example.dot__simple_habit_tracker.domain.models.Habit
import com.example.dot__simple_habit_tracker.ui.models.HabitSuggestion
import com.example.dot__simple_habit_tracker.ui.viewmodels.HabitsViewModel
import kotlinx.coroutines.launch

@Composable
fun InitAddHabitScreen(viewModel: HabitsViewModel, backAction: () -> Unit) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                AddHabitScreenHeader(backArrowAction = backAction)

                HorizontalDivider(thickness = 1.dp)

                Spacer(Modifier.size(25.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Column {
                        AddHabitScreenMotivationalCard()

                        Spacer(Modifier.height(25.dp))

                        HabitInputForm(
                            viewModel = viewModel,
                            onHabitAdded = { habitText ->
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = context.getString(
                                            R.string.habit_added_snack_bar,
                                            habitText
                                        )
                                    )
                                }
                            }
                        )

                        Spacer(Modifier.height(25.dp))

                        SuggestedHabitsSection(
                            viewModel = viewModel,
                            onHabitAdded = { newHabitName ->
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = context.getString(
                                            R.string.habit_added_snack_bar,
                                            newHabitName
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AddHabitScreenHeader(backArrowAction: () -> Unit) {
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
                onClick = { backArrowAction() },
                color = Color.Transparent
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "close",
                    modifier = Modifier
                        .size(35.dp)
                )
            }

            Text(
                text = stringResource(R.string.add_habit_screen_title),
                style = MaterialTheme.typography
                    .headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.size(35.dp))
        }
    }
}

@Composable
fun getRandomMotivationalPhrase(): String {
    val motivationalPhrases = listOf(
        stringResource(id = R.string.motivational_phrase_1),
        stringResource(id = R.string.motivational_phrase_2),
        stringResource(id = R.string.motivational_phrase_3),
        stringResource(id = R.string.motivational_phrase_4),
        stringResource(id = R.string.motivational_phrase_5),
        stringResource(id = R.string.motivational_phrase_6),
        stringResource(id = R.string.motivational_phrase_7),
        stringResource(id = R.string.motivational_phrase_8),
        stringResource(id = R.string.motivational_phrase_9),
        stringResource(id = R.string.motivational_phrase_10)
    )

    return motivationalPhrases.random()
}

@Composable
private fun AddHabitScreenMotivationalCard() {
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
private fun HabitInputForm(viewModel: HabitsViewModel, onHabitAdded: (String) -> Unit) {
    var habitFieldValue: String by remember { mutableStateOf("") }

    Column {
        TextField(
            value = habitFieldValue,
            onValueChange = { habitFieldValue = it },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.add_habit_placeholder),
                    fontStyle = FontStyle.Italic
                )
                          },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.size(5.dp))

        Button(
            onClick = {
                if (habitFieldValue.isNotBlank()) {
                    val newHabit = Habit(name = habitFieldValue.trim())
                    viewModel.addHabit(newHabit)
                    habitFieldValue = ""

                    onHabitAdded(newHabit.name)
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50),
                contentColor = Color.White

            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.add_habit),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun getSuggestedHabits(): List<HabitSuggestion> {
    val suggestedHabitsString: List<String> = listOf(
        stringResource(id  = R.string.habit_suggestion_1),
        stringResource(id  = R.string.habit_suggestion_2),
        stringResource(id  = R.string.habit_suggestion_3),
        stringResource(id  = R.string.habit_suggestion_4),
        stringResource(id  = R.string.habit_suggestion_5),
    )

    val suggestedHabits: List<HabitSuggestion> = suggestedHabitsString.map { habitString ->
        val habitParts = habitString.split("|")
        HabitSuggestion(
            habit = Habit(name = habitParts[0]),
            impactTagLine = if (habitParts.size < 3) "" else habitParts[1],
            description = if (habitParts.size < 3) habitParts[1] else habitParts[2]
            )
    }

    return suggestedHabits
}

@Composable
private fun SuggestedHabitsSection(
    viewModel: HabitsViewModel,
    onHabitAdded: (String) -> Unit
) {
    val suggestedHabits: List<HabitSuggestion> = getSuggestedHabits()

    Surface(
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.habit_suggestions_label),
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 19.sp),
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {
                itemsIndexed(suggestedHabits) { index, habitSuggestion: HabitSuggestion ->
                    SuggestedHabitItem(
                        habitSuggestion = habitSuggestion,
                        isLastSuggestion = index == suggestedHabits.lastIndex,
                        viewModel = viewModel,
                        onSuggestionAccepted = onHabitAdded
                    )
                }
            }
        }
    }
}

@Composable
private fun SuggestedHabitItem(
    habitSuggestion: HabitSuggestion,
    isLastSuggestion: Boolean,
    viewModel: HabitsViewModel,
    onSuggestionAccepted: (String) -> Unit
) {
    val habitList: List<Habit> by viewModel.habits.collectAsState(emptyList())
    val isSuggestionAdded: Boolean = habitList.any { it.name == habitSuggestion.habit.name }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (isLastSuggestion) 0.dp else 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append("${habitSuggestion.habit.name}: ")
                        }
                        withStyle(
                            style = SpanStyle(fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF4CAF50))
                        ) {
                            if (habitSuggestion.impactTagLine.isNotBlank()) {
                                append("${habitSuggestion.impactTagLine} ")
                            }
                        }
                        append(habitSuggestion.description)
                    },
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 19.sp)
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(5.dp),
            color = Color(0xFF4CAF50),
            onClick = {
                if (!isSuggestionAdded) {
                    val newHabit = habitSuggestion.habit.copy()
                    viewModel.addHabit(newHabit)

                    onSuggestionAccepted(newHabit.name)
                }
            }
        ) {
            Icon(
                imageVector = if (isSuggestionAdded) Icons.Filled.Check else Icons.Filled.Add,
                contentDescription =
                    if (isSuggestionAdded) stringResource(R.string.added_habit_suggestion_content_description)
                    else stringResource(R.string.add_habit_suggestion_content_description),
                tint = Color.White,
                modifier = Modifier
                    .size(35.dp)
            )
        }
    }
}
