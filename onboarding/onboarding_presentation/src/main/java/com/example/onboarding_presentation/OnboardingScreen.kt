package com.example.onboarding_presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.components.OnboardingPager

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {

    LaunchedEffect(key1 = viewModel.hasSeenOnboarding) {
        if(viewModel.hasSeenOnboarding)
            onFinish()
    }

    val pages = listOf(
        OnboardingPagerInformation(
            image = R.drawable.onboarding1,
            title = "Welcome to monumental habits",
            subtitle = "we can help you to a better version of yourself"
        ),
        OnboardingPagerInformation(
            image = R.drawable.onboarding2,
            title = "Create a new habit easily",
            subtitle = "we can help you to a better version of yourself"
        ),
        OnboardingPagerInformation(
            image = R.drawable.onboarding3,
            title = "Keep track of you progress",
            subtitle = "we can help you to a better version of yourself"
        ),
        OnboardingPagerInformation(
            image = R.drawable.onboarding4,
            title = "Join a suportive comunity",
            subtitle = "we can help you to a better version of yourself"
        )
    )

    OnboardingPager(pages = pages, modifier = Modifier.fillMaxSize()) {
        viewModel.completeOnboarding()
    }
}