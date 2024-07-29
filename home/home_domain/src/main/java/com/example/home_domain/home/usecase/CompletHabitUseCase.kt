package com.example.home_domain.home.usecase

import com.example.home_domain.models.Habit
import com.example.home_domain.repository.HomeRepository
import java.time.ZonedDateTime

class CompletHabitUseCase (
    private val repository: HomeRepository
) {
    suspend operator fun invoke(habit: Habit, date:ZonedDateTime) {
        val newHabit = if(habit.completedDate.contains(date.toLocalDate()))
        {
            habit.copy(
                completedDate = habit.completedDate - date.toLocalDate()
            )
        }
        else
        {
            habit.copy(
                completedDate = habit.completedDate + date.toLocalDate()
            )
        }
        repository.insertHabit(newHabit)
    }
}