package com.example.onboarding_domain.usecase

import com.example.onboarding_domain.repository.OnboardingRepository

class HasSeenOnboardingUseCase (
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Boolean {
        return repository.hasSeenOnboarding()
    }
}