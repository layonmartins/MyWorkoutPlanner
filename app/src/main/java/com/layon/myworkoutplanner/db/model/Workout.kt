package com.layon.myworkoutplanner.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "Workout")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerialName("workout_name")
    val name: String,
    @SerialName("workout_note")
    val note: String
)
