package com.layon.myworkoutplanner.ui

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.layon.myworkoutplanner.ui.components.DeleteIconButton
import com.layon.myworkoutplanner.ui.components.EditIconButton
import com.layon.myworkoutplanner.ui.theme.MyWorkoutPlannerTheme
import com.layon.myworkoutplanner.ui.theme.md_theme_light_secondary

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

val exercises2 = listOf(
    Pair(0, "Barbell Incline Bench Press"),
    Pair(1, "Dumbbell Bench Press (Flat Bench) Flat"),
    Pair(2, "Chest Fly"),
)

val note =
    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
//val note = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."

@Composable
fun WorkoutPlannerDetailScreen(
    exercises: List<Pair<Int, String>>,
    note: String,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = padding.calculateTopPadding() + 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxHeight(0.7f),
        ) {
            items(exercises) { exercise ->
                WorkoutItem(
                    exerciseName = exercise,
                    onItemClick = {
                        Log.d(TAG, "WorkoutPlannerDetailScreen - exerciseName clicked id: $it")
                    },
                    onEditItemClick = {},
                    onDeletedItemClick = {})
                HorizontalDivider()
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp + padding.calculateBottomPadding())
                .border(
                    width = 2.dp,
                    color = md_theme_light_secondary,
                    shape = RoundedCornerShape(5.dp)
                )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "Note: ",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp),
                text = note
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutPlannerDetailScreenPreview() {
    MyWorkoutPlannerTheme {
        WorkoutPlannerDetailScreen(exercises, note, PaddingValues())
    }
}

@Composable
fun WorkoutItem(
    exerciseName: Pair<Int, String>,
    onItemClick: (Int) -> Unit = { },
    onEditItemClick: (Int) -> Unit = { },
    onDeletedItemClick: (Int) -> Unit = { }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                onItemClick(exerciseName.first)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = exerciseName.second,
            fontSize = 18.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            EditIconButton(
                onClick = {
                    onEditItemClick(exerciseName.first)
                    Log.d(
                        TAG,
                        "WorkoutPlannerDetailScreen - Edit button clicked id: ${exerciseName.first}"
                    )
                }
            )
            DeleteIconButton(
                onClick = {
                    onDeletedItemClick(exerciseName.first)
                    Log.d(
                        TAG,
                        "WorkoutPlannerDetailScreen - Delete button clicked id: ${exerciseName.first}"
                    )
                }
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