package com.example.home_domain.home.usecase

import com.example.home_domain.repository.HomeRepository

class SyncHabitUseCase (
    private val repository: HomeRepository
) {
    suspend operator fun invoke() {
        repository.syncHabits()
    }

}