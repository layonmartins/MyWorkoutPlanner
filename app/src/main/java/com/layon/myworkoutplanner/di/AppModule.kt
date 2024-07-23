package com.layon.myworkoutplanner.di

import android.content.Context
import androidx.room.Room
import com.layon.myworkoutplanner.db.WorkoutPlannerDatabase
import com.layon.myworkoutplanner.db.repository.WorkoutDetailRepository
import com.layon.myworkoutplanner.db.repository.WorkoutDetailRepositoryImpl
import com.layon.myworkoutplanner.db.repository.WorkoutRepository
import com.layon.myworkoutplanner.db.repository.WorkoutRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWorkoutPlannerDB(@ApplicationContext context : Context) : WorkoutPlannerDatabase =
        Room.databaseBuilder(
            context,
            WorkoutPlannerDatabase::class.java,
            "workout_planner.db"
        )
        // .addMigrations() later add migrations if change the table
        .build()

    @Provides
    @Singleton
    fun provideWorkoutRepository(db: WorkoutPlannerDatabase) : WorkoutRepository =
        WorkoutRepositoryImpl(db.workoutDao)

    @Provides
    @Singleton
    fun provideWorkoutDetailRepository(db: WorkoutPlannerDatabase) : WorkoutDetailRepository =
        WorkoutDetailRepositoryImpl(db.workoutDetailDao)
}