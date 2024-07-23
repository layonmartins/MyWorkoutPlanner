package com.layon.myworkoutplanner.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.layon.myworkoutplanner.db.dao.WorkoutDao
import com.layon.myworkoutplanner.db.dao.WorkoutDetailDao
import com.layon.myworkoutplanner.db.model.Workout
import com.layon.myworkoutplanner.db.model.WorkoutDetail

@Database(entities = [Workout::class, WorkoutDetail::class], version = 1)
abstract class WorkoutPlannerDatabase : RoomDatabase() {
    abstract val workoutDao: WorkoutDao
    abstract val workoutDetailDao: WorkoutDetailDao
}