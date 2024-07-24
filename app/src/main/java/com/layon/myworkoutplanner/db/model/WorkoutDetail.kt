package com.layon.myworkoutplanner.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "WorkoutDetail")
data class WorkoutDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerialName("workout_detail_name")
    val name: String,
    @SerialName("workout_id")
    val foreignKey: Int = 0
)