package com.layon.myworkoutplanner.data

import com.layon.myworkoutplanner.db.model.Workout
import com.layon.myworkoutplanner.db.model.WorkoutDetail

data class WorkoutUiState(
    val workoutSelected : Workout = Workout(),
    val workoutDetailSelected : WorkoutDetail = WorkoutDetail(),
    val workoutList: List<Workout> = emptyList(),
    val workoutDetailList: List<WorkoutDetail> = emptyList()
)


