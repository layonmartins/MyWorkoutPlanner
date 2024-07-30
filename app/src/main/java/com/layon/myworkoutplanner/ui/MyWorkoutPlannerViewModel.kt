package com.layon.myworkoutplanner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.layon.myworkoutplanner.data.WorkoutUiState
import com.layon.myworkoutplanner.db.model.Workout
import com.layon.myworkoutplanner.db.model.WorkoutDetail
import com.layon.myworkoutplanner.db.repository.WorkoutDetailRepository
import com.layon.myworkoutplanner.db.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO bugFix: Some times when add new workoutDetail the list is not updated or updated with wrong values
// TODO implement unit test
// TODO Fix the previews
// TODO remove the hardcoded values exercises and exercisesGroup

@HiltViewModel
class MyWorkoutPlannerViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val workoutDetailRepository: WorkoutDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState : StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    init {
        getWorkoutList()
    }

    fun setWorkoutSelected(workout: Workout) {
        _uiState.update { currentState ->
            currentState.copy(
                workoutSelected = workout
            )
        }
    }

    fun getWorkoutList() {
        viewModelScope.launch(IO) {
            workoutRepository.getAll().collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(
                        workoutList = it
                    )
                }
            }
        }
    }

    fun insertWorkout(workout: Workout) {
        viewModelScope.launch(IO) {
            workoutRepository.insert(workout)
        }
    }

    fun updateWorkout() {
        viewModelScope.launch(IO) {
            workoutRepository.update(_uiState.value.workoutSelected)
        }
    }

    fun deleteWorkout() {
        viewModelScope.launch(IO) {
            workoutRepository.delete(_uiState.value.workoutSelected)
            workoutDetailRepository.deleteAll(_uiState.value.workoutSelected.id)
        }
    }

    fun setWorkoutDetailSelected(workoutDetail: WorkoutDetail) {
        _uiState.update { currentState ->
            currentState.copy(
                workoutDetailSelected = workoutDetail
            )
        }
    }

    fun getWorkoutDetailList() {
        viewModelScope.launch(IO) {
            workoutDetailRepository.getAll(_uiState.value.workoutSelected.id).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(
                        workoutDetailList = it
                    )
                }
            }
        }
    }

    fun insertWorkoutDetail(workoutDetail: WorkoutDetail) {
        viewModelScope.launch(IO) {
            workoutDetailRepository.insert(workoutDetail)
        }
    }

    fun updateWorkoutDetail() {
        viewModelScope.launch(IO) {
            workoutDetailRepository.update(_uiState.value.workoutDetailSelected)
        }
    }

    fun deleteWorkoutDetail() {
        viewModelScope.launch(IO) {
            workoutDetailRepository.delete(_uiState.value.workoutDetailSelected)
        }
    }

}