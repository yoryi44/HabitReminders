package com.example.home_domain.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

data class Habit(
    val id:String,
    val name:String,
    val frequency:List<DayOfWeek>,
    val completedDate:List<LocalDate>,
    val reminder: LocalTime,
    val startDay: ZonedDateTime
)
