package com.layon.myworkoutplanner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.layon.myworkoutplanner.db.model.Workout
import com.layon.myworkoutplanner.db.model.WorkoutDetail
import com.layon.myworkoutplanner.db.repository.WorkoutDetailRepository
import com.layon.myworkoutplanner.db.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO implement UiState https://medium.com/@MrAndroid/android-architecture-with-mvvm-and-uistate-f29aa5494465
// https://developer.android.com/topic/architecture/ui-layer/stateholders
// TODO implement unit test
// TODO Fix the previews

@HiltViewModel
class MyWorkoutPlannerViewModel @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val workoutDetailRepository: WorkoutDetailRepository
) : ViewModel() {

    private val _workoutList = MutableStateFlow(emptyList<Workout>())
    val workoutList = _workoutList.asStateFlow()
    internal var workoutSelected = Workout(name = "", note = "")

    fun getWorkoutList() {
        viewModelScope.launch(IO) {
            workoutRepository.getAll().collectLatest {
                _workoutList.tryEmit(it)
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
            workoutRepository.update(workoutSelected)
        }
    }

    fun deleteWorkout() {
        viewModelScope.launch(IO) {
            workoutRepository.delete(workoutSelected)
            workoutDetailRepository.deleteAll(workoutSelected.id)
        }
    }

    private val _workoutDetailList = MutableStateFlow(emptyList<WorkoutDetail>())
    val workoutDetailList = _workoutDetailList.asStateFlow()
    internal var workoutDetailSelected = WorkoutDetail(name = "")

    fun getWorkoutDetailList() {
        viewModelScope.launch(IO) {
            workoutDetailRepository.getAll(workoutSelected.id).collectLatest {
                _workoutDetailList.tryEmit(it)
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
            workoutDetailRepository.update(workoutDetailSelected)
        }
    }

    fun deleteWorkoutDetail() {
        viewModelScope.launch(IO) {
            workoutDetailRepository.delete(workoutDetailSelected)
        }
    }

}