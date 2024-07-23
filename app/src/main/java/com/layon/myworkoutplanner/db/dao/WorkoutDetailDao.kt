package com.layon.myworkoutplanner.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.layon.myworkoutplanner.db.model.WorkoutDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workoutDetail: WorkoutDetail)

    @Delete
    suspend fun delete(workoutDetail: WorkoutDetail)

    @Update
    suspend fun update(workoutDetail: WorkoutDetail)

    @Query("SELECT * FROM WorkoutDetail WHERE id = :workoutId")
    fun getAll(workoutId: Int) : Flow<List<WorkoutDetail>>

}