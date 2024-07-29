package com.example.home_presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_domain.home.usecase.CompletHabitUseCase
import com.example.home_domain.home.usecase.GetHabitsForDateUseCase
import com.example.home_domain.home.usecase.SyncHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitsForDateUseCase: GetHabitsForDateUseCase,
    private val completaHabitUseCase: CompletHabitUseCase,
    private val syncHabitUseCase: SyncHabitUseCase
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private var currentDayJob : Job? = null

    init {
        getHabits()
        viewModelScope.launch {
            syncHabitUseCase()
        }
    }

    fun onEvent(event: HomeEvent)
    {
        when(event)
        {
            is HomeEvent.changeDate -> {
                state = state.copy(
                    selectDate = event.date
                )
                getHabits()
            }
            is HomeEvent.completeHabit -> {
                viewModelScope.launch {
                    completaHabitUseCase(event.habit, state.selectDate)
                }
            }
        }
    }
    private fun getHabits()
    {
        currentDayJob?.cancel()
        currentDayJob = viewModelScope.launch {
            getHabitsForDateUseCase(state.selectDate).collectLatest {
                state = state.copy(
                    habits = it
                )
            }
        }
    }
}