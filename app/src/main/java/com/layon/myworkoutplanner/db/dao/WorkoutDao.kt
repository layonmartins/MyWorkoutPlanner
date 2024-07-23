package com.layon.myworkoutplanner.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.layon.myworkoutplanner.db.model.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Query("SELECT * FROM Workout")
    fun getAll() : Flow<List<Workout>>

}