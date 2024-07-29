package com.example.habitsappcourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.habitsappcourse.navigation.NavigationHost
import com.example.habitsappcourse.navigation.NavigationRoute
import com.example.habitsappcourse.ui.theme.HabitsAppCourseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitsAppCourseTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding))
                    {
                        val navController = rememberNavController()
                        NavigationHost(
                            navHostController = navController,
                            startDestination = getStartDestination(),
                            logout = {
                                mainViewModel.logout()
                            }
                        )
                    }
                }
            }
        }
    }

    private fun getStartDestination(): NavigationRoute {
        if(mainViewModel.isLoggedIn)
        {
            return NavigationRoute.Home
        }
        return if (mainViewModel.hasSeenOnboarding) {
            NavigationRoute.Login
        } else {
            NavigationRoute.Onboarding
        }
    }
}