package com.example.home_presentation.detail

import java.time.DayOfWeek
import java.time.LocalTime

sealed interface DetailEvent {
    data class ReminderChage(val time:LocalTime): DetailEvent
    data class FrecuencyChage(val dayOfWeek: DayOfWeek): DetailEvent
    data class NameChage(val name:String): DetailEvent
    object HabitSave: DetailEvent
}