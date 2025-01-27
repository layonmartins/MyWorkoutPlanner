package com.layon.myworkoutplanner.ui

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.layon.myworkoutplanner.db.model.WorkoutDetail
import com.layon.myworkoutplanner.ui.components.CustomDialog
import com.layon.myworkoutplanner.ui.components.DeleteDialog
import com.layon.myworkoutplanner.ui.components.DeleteIconButton
import com.layon.myworkoutplanner.ui.components.EditIconButton
import com.layon.myworkoutplanner.ui.components.SaveIconButton
import com.layon.myworkoutplanner.ui.theme.MyWorkoutPlannerTheme
import com.layon.myworkoutplanner.ui.theme.md_theme_light_secondary
import kotlinx.coroutines.delay

val exercises = listOf(
    Pair(0, "Barbell Incline Bench Press"),
    Pair(1, "Dumbbell Bench Press (Flat Bench) Flat"),
    Pair(2, "Chest Fly"),
    Pair(3, "Seated dumbbell shoulder press"),
    Pair(4, "Standing Lateral Dumbbell Raise"),
    Pair(5, "Dumbbell front raise"),
    Pair(6, "Close-grip bench press"),
    Pair(7, "Triceps Pushdown"),
    Pair(8, "Lorem Ipsum is simply dummy text of the printing and typesetting industry!"),
    Pair(9, "AAAA"),
    Pair(10, "BBBB"),
    Pair(11, "CCC"),
    Pair(12, "DDD"),
    Pair(13, "EE"),
    Pair(14, "FF"),
    Pair(15, "GG"),
)

val note =
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."

@Composable
fun WorkoutPlannerDetailScreen(
    viewModel: MyWorkoutPlannerViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val shouldShowDeleteDialog = rememberSaveable { mutableStateOf(false) }
    if (shouldShowDeleteDialog.value) {
        DeleteDialog(
            workoutName = uiState.workoutDetailSelected.name,
            onDismissRequest = {
                shouldShowDeleteDialog.value = false
            },
            onConfirmation = {
                Log.d(TAG, "WorkoutPlannerDetailScreen - Delete confirmation clicked workoutDetailSelected : ${uiState.workoutDetailSelected}")
                viewModel.deleteWorkoutDetail()
                shouldShowDeleteDialog.value = false
            })
    }

    val shouldShowEditScreen = rememberSaveable { mutableStateOf(false) }
    if (shouldShowEditScreen.value) {
        CustomDialog(
            value = uiState.workoutDetailSelected.name,
            dialogTitle = "Edit Workout Name",
            setShowDialog = { shouldShowEditScreen.value = it },
            setValue = {
                Log.d(TAG, "WorkoutPlannerDetailScreen - Edit confirmation clicked: $it")
                viewModel.setWorkoutDetailSelected(uiState.workoutDetailSelected.copy(name = it))
                viewModel.updateWorkoutDetail()
            }
        )
    }

    val shouldShowAddScreen = rememberSaveable { mutableStateOf(false) }
    if (shouldShowAddScreen.value) {
        CustomDialog(
            value = "",
            dialogTitle = "Add New Workout",
            setShowDialog = { shouldShowAddScreen.value = it },
            setValue = {
                Log.d(TAG, "WorkoutPlannerDetailScreen - Save confirmation clicked: $it")
                val newWorkoutDetail = WorkoutDetail(
                    name = it,
                    foreignKey = uiState.workoutSelected.id
                )
                viewModel.insertWorkoutDetail(newWorkoutDetail)
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = uiState.workoutSelected.name,
            fontSize = 22.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis
        )
        HorizontalDivider()
        Box(
            modifier = Modifier.fillMaxHeight(0.6f)
        ) {
            LazyColumn {
                items(uiState.workoutDetailList) { workoutDetail ->
                    WorkoutItem(
                        workout = workoutDetail.id to workoutDetail.name,
                        onItemClick = {
                            Log.d(TAG, "WorkoutPlannerDetailScreen - exerciseName clicked id: ${workoutDetail.id}")
                            // do nothing
                        },
                        onEditItemClick = {
                            Log.d(TAG, "WorkoutPlannerDetailScreen - Edit button clicked id: ${workoutDetail.id}")
                            viewModel.setWorkoutDetailSelected(workoutDetail)
                            shouldShowEditScreen.value = true
                        },
                        onDeletedItemClick = {
                            Log.d(TAG, "WorkoutPlannerDetailScreen - Delete button clicked: $workoutDetail")
                            viewModel.setWorkoutDetailSelected(workoutDetail)
                            shouldShowDeleteDialog.value = true
                        }
                    )
                    HorizontalDivider()
                }
                // Add a spacer item at the end of lazy column
                item {
                    Spacer(modifier = Modifier.height(72.dp))
                }
            }
            AddButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                onClick = {
                    Log.d(TAG, "WorkoutPlannerDetailScreen - Add button clicked")
                    shouldShowAddScreen.value = true
                })
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        NoteItem(uiState.workoutSelected.note) {
            viewModel.setWorkoutSelected(uiState.workoutSelected.copy(note = it))
            viewModel.updateWorkout()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutPlannerDetailScreenPreview() {
    MyWorkoutPlannerTheme {
        val mockWorkoutPlannerViewModel: MyWorkoutPlannerViewModel = viewModel()
        WorkoutPlannerDetailScreen(mockWorkoutPlannerViewModel)
    }
}

@Composable
fun WorkoutItem(
    workout: Pair<Int, String>,
    isBold: Boolean = false,
    onItemClick: (Int) -> Unit = { },
    onEditItemClick: (Int) -> Unit = { },
    onDeletedItemClick: (Int) -> Unit = { }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                onItemClick(workout.first)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.7f),
            text = workout.second,
            fontSize = 18.sp,
            maxLines = 2,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            EditIconButton(
                onClick = { onEditItemClick(workout.first) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DeleteIconButton(
                onClick = { onDeletedItemClick(workout.first) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutItemPreview() {
    MyWorkoutPlannerTheme {
        WorkoutItem(exercises[0])
    }
}

@Composable
fun NoteItem(
    noteString: String,
    onSaveItemClick: (String) -> Unit = { }
) {
    val isNoteInEditionMode = rememberSaveable { mutableStateOf(false) }
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = noteString, selection = when {
                    noteString.isEmpty() -> TextRange.Zero
                    else -> TextRange(noteString.length, noteString.length)
                }
            )
        )
    }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .border(
                width = 2.dp,
                color = md_theme_light_secondary,
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = "Note: ",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            if (isNoteInEditionMode.value) {
                SaveIconButton(
                    onClick = {
                        Log.d(TAG, "NoteItem - Save confirmation clicked : ${textFieldValueState.text}")
                        isNoteInEditionMode.value = false
                        onSaveItemClick(textFieldValueState.text)
                    }
                )
            } else {
                EditIconButton(
                    onClick = {
                        isNoteInEditionMode.value = true
                    }
                )
            }
        }
        if (isNoteInEditionMode.value) {
            TextField(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .focusRequester(focusRequester),
                value = textFieldValueState,
                onValueChange = { textFieldValueState = it },
            )
            // Launch an effect to request focus after a short delay
            LaunchedEffect(Unit) {
                delay(100)
                focusRequester.requestFocus()
            }
        } else {
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp),
                text = textFieldValueState.text
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun NoteItemPreview() {
    MyWorkoutPlannerTheme {
        NoteItem(note)
    }
}