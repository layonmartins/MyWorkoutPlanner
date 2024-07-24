package com.layon.myworkoutplanner.db.repository

import com.layon.myworkoutplanner.db.model.WorkoutDetail
import kotlinx.coroutines.flow.Flow

interface WorkoutDetailRepository {
    suspend fun insert(workoutDetail: WorkoutDetail)
    suspend fun delete(workoutDetail: WorkoutDetail)
    suspend fun update(workoutDetail: WorkoutDetail)
    suspend fun getAll(workoutId: Int) : Flow<List<WorkoutDetail>>
    suspend fun deleteAll(workoutId: Int)
}