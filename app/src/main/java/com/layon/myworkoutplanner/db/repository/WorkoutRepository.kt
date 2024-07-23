package com.layon.myworkoutplanner.db.repository

import com.layon.myworkoutplanner.db.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun insert(workout: Workout)
    suspend fun delete(workout: Workout)
    suspend fun update(workout: Workout)
    suspend fun getAll() : Flow<List<Workout>>
}