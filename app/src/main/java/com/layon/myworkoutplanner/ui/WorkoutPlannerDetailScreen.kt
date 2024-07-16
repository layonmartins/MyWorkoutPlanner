package com.layon.myworkoutplanner.ui

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.layon.myworkoutplanner.ui.components.CustomDialog
import com.layon.myworkoutplanner.ui.components.DeleteDialog
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

    val shouldShowDeleteDialog = rememberSaveable { mutableStateOf(false) }
    val workoutDetailName = rememberSaveable { mutableStateOf("") }
    if (shouldShowDeleteDialog.value) {
        DeleteDialog(
            workoutName = workoutDetailName,
            onDismissRequest = {
                shouldShowDeleteDialog.value = false
            },
            onConfirmation = {
                Log.d(TAG, "WorkoutPlannerDetailScreen - Delete confirmation clicked")
                //viewModel.delete(viewModel.getSelectedWorkoutPlanner())
                //close DeleteDialog
            })
    }

    val shouldShowEditScreen = rememberSaveable { mutableStateOf(false) }
    if (shouldShowEditScreen.value) {
        CustomDialog(
            value = workoutDetailName.value,
            dialogTitle = "Edit Workout Name",
            setShowDialog = { shouldShowEditScreen.value = it },
            setValue = {
                Log.d(TAG, "WorkoutPlannerDetailScreen - Edit confirmation clicked: $it")
                //viewModel.save(viewModel.getSelectedWorkoutPlanner())
                //close CustomDialog
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
                //viewModel.save(viewModel.getSelectedWorkoutPlanner())
                //close CustomDialog
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = "Workout Planner Name",
            fontSize = 22.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis
        )
        HorizontalDivider()
        Box(
            modifier = Modifier.fillMaxHeight(0.7f)
        ) {
            LazyColumn {
                items(exercises) { exercise ->
                    WorkoutItem(
                        name = exercise,
                        onItemClick = {
                            Log.d(TAG, "WorkoutPlannerDetailScreen - exerciseName clicked id: $it")
                        },
                        onEditItemClick = {
                            Log.d(TAG, "WorkoutPlannerDetailScreen - Edit button clicked id: $it")
                            //viewModel.setSelectedWorkoutDetailName(it)
                            workoutDetailName.value = exercise.second
                            shouldShowEditScreen.value = true
                        },
                        onDeletedItemClick = {
                            Log.d(TAG, "WorkoutPlannerDetailScreen - Delete button clicked id: $it")
                            //viewModel.setSelectedWorkoutDetailName(it)
                            workoutDetailName.value = exercise.second
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
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
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
    name: Pair<Int, String>,
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
                onItemClick(name.first)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.7f),
            text = name.second,
            fontSize = 18.sp,
            maxLines = 2,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            EditIconButton(
                onClick = { onEditItemClick(name.first) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DeleteIconButton(
                onClick = { onDeletedItemClick(name.first) }
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