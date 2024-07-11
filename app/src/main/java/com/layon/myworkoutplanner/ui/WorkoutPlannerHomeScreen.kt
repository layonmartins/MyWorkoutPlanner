package com.layon.myworkoutplanner.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    exercisesGroup: List<Pair<Int,String>>,
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = padding.calculateTopPadding() + 8.dp),
    ){
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                items(exercisesGroup) { exercisesGroup ->
                    WorkoutItem(
                        name = exercisesGroup,
                        isBold = true,
                        onItemClick = {
                            Log.d(TAG, "WorkoutPlannerHomeScreen - exerciseName clicked id: $it")
                        },
                        onEditItemClick = {
                            Log.d(TAG, "WorkoutPlannerHomeScreen - Edit button clicked id: $it")
                        },
                        onDeletedItemClick = {
                            Log.d(TAG, "WorkoutPlannerHomeScreen - Delete button clicked id: $it}")
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
        AddButton(
            modifier = Modifier.align(Alignment.BottomEnd).padding(vertical = padding.calculateTopPadding(), horizontal = 8.dp),
            onClick = {
            Log.d(TAG, "WorkoutPlannerHomeScreen - Add button clicked")
        })
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutPlannerHomeScreenPreview() {
    MyWorkoutPlannerTheme {
        WorkoutPlannerHomeScreen(exercisesGroup, PaddingValues())
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
