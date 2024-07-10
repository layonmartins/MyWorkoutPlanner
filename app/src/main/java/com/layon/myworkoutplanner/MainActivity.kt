package com.layon.myworkoutplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.layon.myworkoutplanner.ui.WorkoutPlannerDetailScreen
import com.layon.myworkoutplanner.ui.WorkoutPlannerHomeScreen
import com.layon.myworkoutplanner.ui.exercises
import com.layon.myworkoutplanner.ui.exercisesGroup
import com.layon.myworkoutplanner.ui.note
import com.layon.myworkoutplanner.ui.theme.MyWorkoutPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWorkoutPlannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WorkoutPlannerDetailScreen(
                        exercises,
                        note,
                        innerPadding
                    )
                }
            }
        }
    }
}

