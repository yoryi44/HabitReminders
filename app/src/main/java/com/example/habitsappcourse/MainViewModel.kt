package com.example.habitsappcourse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication_domain.usecase.GetUserIdUseCase
import com.example.authentication_domain.usecase.LogoutUseCase
import com.example.onboarding_domain.usecase.CompleteOnboardingUseCase
import com.example.onboarding_domain.usecase.HasSeenOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val hasSeenOnboardingUseCase: com.example.onboarding_domain.usecase.HasSeenOnboardingUseCase,
    private val getUserIdUseCase: com.example.authentication_domain.usecase.GetUserIdUseCase,
    private val logoutUseCase: com.example.authentication_domain.usecase.LogoutUseCase
) : ViewModel() {

    var hasSeenOnboarding by mutableStateOf(hasSeenOnboardingUseCase())
        private set

    var isLoggedIn by mutableStateOf(getUserIdUseCase() != null)
        private set

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
        }
    }

}