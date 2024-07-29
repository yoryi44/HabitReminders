package com.example.home_presentation.home


import com.example.home_domain.models.Habit
import java.time.ZonedDateTime

data class HomeState(
    val currentDate:ZonedDateTime = ZonedDateTime.now(),
    val selectDate:ZonedDateTime = ZonedDateTime.now(),
    val habits: List<Habit> = emptyList()
)

