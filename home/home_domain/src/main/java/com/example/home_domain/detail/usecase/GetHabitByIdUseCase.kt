package com.example.home_domain.detail.usecase

import com.example.home_domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GetHabitByIdUseCase(
    private val repository: HomeRepository
){
    suspend operator fun invoke(id: String): com.example.home_domain.models.Habit {
        return withContext(Dispatchers.IO) {repository.getHabitById(id)}
    }
}