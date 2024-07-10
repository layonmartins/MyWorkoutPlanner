package com.layon.myworkoutplanner.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.layon.myworkoutplanner.ui.components.EditIconButton
import com.layon.myworkoutplanner.ui.theme.MyWorkoutPlannerTheme

const val TAG = "layonflog"

val exercisesGroup  = listOf(
    Pair(0, "A - Chest and Shoulder"),
    Pair(1, "B - Back and biceps"),
    Pair(2, "C - Legs"),
    Pair(3, "D - Cardio")
)
@Composable
fun WorkoutPlannerHomeScreen(
    exercisesGroup: List<Pair<Int,String>>,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding.calculateTopPadding() + 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(exercisesGroup) { exercisesGroup ->
                WorkoutGroupItem(exercisesGroup) {
                    Log.d(TAG, "WorkoutPlannerHomeScreen - WorkoutGroupItem clicked id: $it")
                }
                HorizontalDivider()
            }
        }
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
fun WorkoutGroupItem(
    exerciseGroupName: Pair<Int, String>,
    onItemClick: (Int) -> Unit = { }) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable {
                onItemClick(exerciseGroupName.first)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = exerciseGroupName.second,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        EditIconButton(
            onClick = { Log.d(TAG, "WorkoutPlannerHomeScreen - Edit button clicked id: ${exerciseGroupName.first}")}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutGroupItemPreview() {
    MyWorkoutPlannerTheme {
        WorkoutGroupItem(exercisesGroup[0])
    }
}