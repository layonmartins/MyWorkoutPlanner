package com.layon.myworkoutplanner.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.layon.myworkoutplanner.db.model.Workout
import com.layon.myworkoutplanner.ui.components.CustomDialog
import com.layon.myworkoutplanner.ui.components.DeleteDialog
import com.layon.myworkoutplanner.ui.theme.MyWorkoutPlannerTheme

const val TAG = "layonflog"

val exercisesGroup2  = listOf(
    Pair(0, "A - Chest and Shoulder"),
    Pair(1, "B - Back and biceps"),
    Pair(2, "C - Legs"),
    Pair(3, "D - Cardio")
)

val exercisesGroup  = listOf(
    Pair(0, "A - Chest and Shoulder"),
    Pair(1, "B - Back and biceps"),
    Pair(2, "C - Legs"),
    Pair(3, "D - Cardio"),
    Pair(3, "D - Exercises"),
    Pair(3, "D - Exercises"),
    Pair(3, "D - Exercises"),
    Pair(3, "D - Exercises"),
    Pair(3, "D - Exercises"),
    Pair(3, "D - Exercises"),
    Pair(3, "D - Exercises"),
    Pair(3, "D - Exercises"),
    Pair(3, "D - Exercises"),
    Pair(3, "E - Exercises"),
    Pair(3, "F - Exercises"),
    Pair(3, "F - Exercises"),
    Pair(3, "G - Exercises"),
)

@Composable
fun WorkoutPlannerHomeScreen(
    viewModel: MyWorkoutPlannerViewModel,
    onItemClick: () -> Unit = { }
) {
    val uiState by viewModel.uiState.collectAsState()

    val shouldShowDeleteDialog = rememberSaveable { mutableStateOf(false) }
    if (shouldShowDeleteDialog.value) {
        DeleteDialog(
            workoutName = uiState.workoutSelected.name,
            onDismissRequest = {
                shouldShowDeleteDialog.value = false
            },
            onConfirmation = {
                Log.d(TAG, "WorkoutPlannerHomeScreen - Delete confirmation clicked")
                viewModel.deleteWorkout()
                shouldShowDeleteDialog.value = false
            })
    }

    val shouldShowEditScreen = rememberSaveable { mutableStateOf(false) }
    if (shouldShowEditScreen.value) {
        CustomDialog(
            value = uiState.workoutSelected.name,
            dialogTitle = "Edit Workout Name",
            setShowDialog = { shouldShowEditScreen.value = it },
            setValue = {
                Log.d(TAG, "WorkoutPlannerHomeScreen - Edit confirmation clicked: $it")
                viewModel.setWorkoutSelected(uiState.workoutSelected.copy(name = it))
                viewModel.updateWorkout()
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
                Log.d(TAG, "WorkoutPlannerHomeScreen - Save confirmation clicked: $it")
                val newWorkout = Workout(name = it, note = "")
                viewModel.insertWorkout(newWorkout)
            }
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(uiState.workoutList) { workout ->
                    WorkoutItem(
                        workout = workout.id to workout.name,
                        isBold = true,
                        onItemClick = {
                            Log.d(TAG, "WorkoutPlannerHomeScreen - exerciseName clicked id: ${workout.id}")
                            viewModel.apply {
                                setWorkoutSelected(workout)
                                getWorkoutDetailList()
                            }
                            onItemClick()
                        },
                        onEditItemClick = {
                            Log.d(TAG, "WorkoutPlannerHomeScreen - Edit button clicked id: ${workout.id}")
                            viewModel.setWorkoutSelected(workout)
                            shouldShowEditScreen.value = true
                        },
                        onDeletedItemClick = {
                            Log.d(TAG, "WorkoutPlannerHomeScreen - Delete button clicked id: ${workout.id}")
                            viewModel.setWorkoutSelected(workout)
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
        }
        AddButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            onClick = {
            Log.d(TAG, "WorkoutPlannerHomeScreen - Add button clicked")
                shouldShowAddScreen.value = true
        })
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutPlannerHomeScreenPreview() {
    MyWorkoutPlannerTheme {
        //WorkoutPlannerHomeScreen(exercisesGroup)
    }
}

@Composable
fun AddButton(
    modifier : Modifier,
    onClick: () -> Unit) {
    FloatingActionButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, "Add floating action button.")
    }
}
