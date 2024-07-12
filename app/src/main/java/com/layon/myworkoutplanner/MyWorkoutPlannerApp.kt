package com.layon.myworkoutplanner

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.layon.myworkoutplanner.ui.MyWorkoutPlannerViewModel
import com.layon.myworkoutplanner.ui.WorkoutPlannerDetailScreen
import com.layon.myworkoutplanner.ui.WorkoutPlannerHomeScreen
import com.layon.myworkoutplanner.ui.exercises
import com.layon.myworkoutplanner.ui.exercisesGroup
import com.layon.myworkoutplanner.ui.note

enum class MyWorkoutPlannerScreen(val title: String) {
    Home(title = "My Workout Planner"),
    WorkoutDetail(title = "Workout Details")
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWorkoutPlannerScreenAppBar(
    currentScreen: MyWorkoutPlannerScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back_button"
                    )
                }
            }
        }
    )
}

@Composable
fun MyWorkoutPlannerApp(
    viewModel: MyWorkoutPlannerViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MyWorkoutPlannerScreen.valueOf(
        backStackEntry?.destination?.route ?: MyWorkoutPlannerScreen.Home.name
    )
    Scaffold(
        topBar = {
            MyWorkoutPlannerScreenAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MyWorkoutPlannerScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MyWorkoutPlannerScreen.Home.name) {
                WorkoutPlannerHomeScreen(
                    exercisesGroup = exercisesGroup,
                    padding = innerPadding,
                    onItemClick = {
                        //TODO pass id parameter or call here viewModel.getDetail(id) ??
                        navController.navigate(MyWorkoutPlannerScreen.WorkoutDetail.name)
                    }
                )
            }
            composable(route = MyWorkoutPlannerScreen.WorkoutDetail.name) {
                WorkoutPlannerDetailScreen(
                    exercises = exercises,
                    note = note,
                    padding = innerPadding
                )
            }
        }
    }
}