package com.example.onboarding_domain.repository

interface OnboardingRepository {
    fun hasSeenOnboarding() : Boolean
    fun completeOnboarding()
}