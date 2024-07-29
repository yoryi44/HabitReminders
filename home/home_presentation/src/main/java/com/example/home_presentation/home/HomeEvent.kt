package com.example.home_presentation.home

import com.example.home_domain.models.Habit
import java.time.ZonedDateTime

sealed interface HomeEvent {
    data class changeDate(val date:ZonedDateTime): HomeEvent
    data class completeHabit(val habit: Habit): HomeEvent
}