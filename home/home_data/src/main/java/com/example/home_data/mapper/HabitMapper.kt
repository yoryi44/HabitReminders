package com.example.home_data.mapper

import com.example.home_data.extension.toStartOfDayTimestamp
import com.example.home_data.extension.toTimeStamp
import com.example.home_data.extension.toZoneDateTime
import com.example.home_data.extension.toZonedDateTime
import com.example.home_data.local.entity.HabitEntity
import com.example.home_data.local.entity.HabitSyncEntity
import com.example.home_data.remote.dto.HabitDto
import com.example.home_data.remote.dto.HabitResponse
import com.example.home_domain.models.Habit
import java.time.DayOfWeek


fun HabitEntity.toDomain() : com.example.home_domain.models.Habit {
    return com.example.home_domain.models.Habit(
        id = this.id,
        startDay = this.startDay.toZonedDateTime(),
        completedDate = this.completedDates.map { it.toZonedDateTime().toLocalDate() },
        name = this.name,
        reminder = this.reminder.toZonedDateTime().toLocalTime(),
        frequency = this.frequency.map { DayOfWeek.of(it) }
    )
}

fun com.example.home_domain.models.Habit.toEntity() : HabitEntity {
    return HabitEntity(
        id = this.id,
        startDay = this.startDay.toStartOfDayTimestamp(),
        completedDates = this.completedDate.map { it.toZoneDateTime().toTimeStamp() },
        name = this.name,
        reminder = this.reminder.toZoneDateTime().toTimeStamp(),
        frequency = this.frequency.map { it.value }
    )
}

fun HabitResponse.toDomain() : List<com.example.home_domain.models.Habit>{
    return this.entries.map {
        val id = it.key
        val habit = it.value
        com.example.home_domain.models.Habit(
            id = id,
            startDay = habit.startDay.toZonedDateTime(),
            completedDate = habit.completedDates?.map { it.toZonedDateTime().toLocalDate() }
                ?: emptyList(),
            name = habit.name,
            reminder = habit.reminder.toZonedDateTime().toLocalTime(),
            frequency = habit.frequency.map { DayOfWeek.of(it) }
        )
    }
}

fun com.example.home_domain.models.Habit.toDto(): HabitResponse {
    val dto = HabitDto(
        name = this.name,
        frequency = this.frequency.map { it.value },
        completedDates = this.completedDate.map { it.toZoneDateTime().toTimeStamp() },
        reminder = this.reminder.toZoneDateTime().toTimeStamp(),
        startDay = this.startDay.toStartOfDayTimestamp()
    )
    return mapOf(id to dto)
}

fun com.example.home_domain.models.Habit.toSyncEntity() : HabitSyncEntity {
    return HabitSyncEntity(id)
}