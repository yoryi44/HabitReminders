package com.example.home_domain.home.usecase

import com.example.home_domain.models.Habit
import com.example.home_domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime

class GetHabitsForDateUseCase (
    private val repository: HomeRepository
) {
    operator fun invoke(date: ZonedDateTime): Flow<List<Habit>> {
        return repository.getAllHabitsForSelectedDate(date).map { it: List<Habit> ->
            it.filter { habit -> habit.frequency.contains(date.dayOfWeek) }
        }.distinctUntilChanged()
    }
}