package com.layon.myworkoutplanner.db.repository

import com.layon.myworkoutplanner.db.dao.WorkoutDetailDao
import com.layon.myworkoutplanner.db.model.WorkoutDetail
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutDetailRepositoryImpl @Inject constructor(
private val dao: WorkoutDetailDao
) : WorkoutDetailRepository {
    override suspend fun insert(workoutDetail: WorkoutDetail) {
        withContext(IO) {
            dao.insert(workoutDetail)
        }
    }

    override suspend fun delete(workoutDetail: WorkoutDetail) {
        withContext(IO) {
            dao.insert(workoutDetail)
        }
    }

    override suspend fun update(workoutDetail: WorkoutDetail) {
        withContext(IO) {
            dao.insert(workoutDetail)
        }
    }

    override suspend fun getAll(workoutId: Int): Flow<List<WorkoutDetail>> {
        return withContext(IO) {
            dao.getAll(workoutId)
        }
    }
}