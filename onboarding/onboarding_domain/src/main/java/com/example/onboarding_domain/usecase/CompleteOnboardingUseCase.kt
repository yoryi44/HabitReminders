package com.example.onboarding_domain.usecase

import com.example.onboarding_domain.repository.OnboardingRepository
class CompleteOnboardingUseCase (
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke() {
        onboardingRepository.completeOnboarding()
    }
}