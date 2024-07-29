package com.example.onboarding_data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.onboarding_data.repository.OnboardingRepositoryImpl
import com.example.onboarding_domain.repository.OnboardingRepository
import com.example.onboarding_domain.usecase.CompleteOnboardingUseCase
import com.example.onboarding_domain.usecase.HasSeenOnboardingUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object onboardingModule {

    @Provides
    @Singleton
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("habit_onboarding_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideOnboardingRepository(sharedPreferences: SharedPreferences): OnboardingRepository {
        return OnboardingRepositoryImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideHasSeenOnboardingUseCase(onboardingRepository: OnboardingRepository): HasSeenOnboardingUseCase {
        return HasSeenOnboardingUseCase(onboardingRepository)
    }

    @Provides
    @Singleton
    fun provideCompleteOnboardingUseCase(onboardingRepository: OnboardingRepository): CompleteOnboardingUseCase {
        return CompleteOnboardingUseCase(onboardingRepository)
    }

}