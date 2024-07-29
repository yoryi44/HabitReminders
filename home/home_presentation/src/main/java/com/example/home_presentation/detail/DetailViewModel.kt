package com.example.home_presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.home_domain.detail.usecase.GetHabitByIdUseCase
import androidx.lifecycle.viewModelScope
import com.example.home_domain.detail.usecase.InsertHabitUseCase
import com.example.home_domain.models.Habit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val insertHabitUseCase: InsertHabitUseCase
) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    init {
        val id = savedStateHandle.get<String>("habitId")
        if(id != null)
        {
            viewModelScope.launch {
                val habit = getHabitByIdUseCase(id)
                state = state.copy(
                    id = habit.id,
                    habitName = habit.name,
                    frequency = habit.frequency,
                    reminder = habit.reminder,
                    completedDates = habit.completedDate,
                    startDay = habit.startDay,
                )
            }
        }
    }

    fun onEvent(event: DetailEvent) {
        when(event)
        {
            is DetailEvent.FrecuencyChage -> {
                val frequency = if(state.frequency.contains(event.dayOfWeek))
                {
                    state.frequency - event.dayOfWeek
                }
                else
                {
                    state.frequency + event.dayOfWeek
                }
                state = state.copy(
                    frequency = frequency
                )
            }
            DetailEvent.HabitSave -> {
                viewModelScope.launch {

                    val habit = Habit(
                        id = state.id ?: UUID.randomUUID().toString(),
                        name = state.habitName,
                        frequency = state.frequency,
                        reminder = state.reminder,
                        completedDate = state.completedDates,
                        startDay = state.startDay
                    )
                    insertHabitUseCase(habit)
                }
                state = state.copy(
                    isSave = true
                )
            }
            is DetailEvent.NameChage -> {
                state = state.copy(
                    habitName = event.name
                )
            }
            is DetailEvent.ReminderChage -> {
                state = state.copy(
                    reminder = event.time
                )
            }
        }
    }
}