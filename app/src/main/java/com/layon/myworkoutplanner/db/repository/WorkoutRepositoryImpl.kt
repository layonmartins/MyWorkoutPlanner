package com.layon.myworkoutplanner.db.repository

import com.layon.myworkoutplanner.db.dao.WorkoutDao
import com.layon.myworkoutplanner.db.model.Workout
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutDao
) : WorkoutRepository {
    override suspend fun insert(workout: Workout) {
        withContext(IO) {
            dao.insert(workout)
        }
    }

    override suspend fun delete(workout: Workout) {
        withContext(IO) {
            dao.delete(workout)
        }
    }

    override suspend fun update(workout: Workout) {
        withContext(IO) {
            dao.update(workout)
        }
    }

    override suspend fun getAll(): Flow<List<Workout>> {
        return withContext(IO) {
            dao.getAll()
        }
    }
}