package com.example.habitsappcourse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.authentication_presentation.login.LoginScreen
import com.example.authentication_presentation.signup.SignupScreen
import com.example.home_presentation.detail.DetailScreen
import com.example.home_presentation.home.HomeScreen
import com.example.onboarding_presentation.OnboardingScreen
import com.example.settings_presentation.SettingsScreen

@Composable
fun NavigationHost(
    navHostController: NavHostController,
    startDestination: NavigationRoute,
    logout: () -> Unit
) {
    NavHost(navController = navHostController, startDestination = startDestination.route) {
        composable(NavigationRoute.Onboarding.route) {
            OnboardingScreen(onFinish = {
                navHostController.popBackStack()
                navHostController.navigate(NavigationRoute.Login.route)
            })
        }
        composable(NavigationRoute.Login.route) {
            LoginScreen(onLogin = {
                navHostController.popBackStack()
                navHostController.navigate(NavigationRoute.Home.route)
            },
                onSignUp = {
                    navHostController.navigate(NavigationRoute.Signup.route)
                }
            )
        }
        composable(NavigationRoute.Home.route) {
            HomeScreen(onNewHabit = {
                navHostController.navigate(NavigationRoute.Detail.route)
            }, onSettings = {
                navHostController.navigate(NavigationRoute.Settings.route)
            }, onEditHabit = {
                navHostController.navigate(NavigationRoute.Detail.route + "?habitId=$it")
            })
        }
        composable(NavigationRoute.Signup.route) {
            SignupScreen(onSignIn = {
                navHostController.navigate(NavigationRoute.Home.route)
                {
                    popUpTo(navHostController.graph.id)
                    {
                        inclusive = true
                    }
                }
            },
                onLogIn = {
                    navHostController.popBackStack()
                    navHostController.navigate(NavigationRoute.Login.route)
                })
        }
        composable(NavigationRoute.Detail.route + "?habitId={habitId}", arguments = listOf(
            navArgument("habitId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ))
        {
            DetailScreen(
                onBack = { navHostController.popBackStack() },
                onSaved = { navHostController.popBackStack() })
        }

        composable(NavigationRoute.Settings.route) {
            SettingsScreen(
                onBack = { navHostController.popBackStack() },
                onLogout = {
                    logout()
                    navHostController.navigate(NavigationRoute.Home.route)
                    {
                        popUpTo(navHostController.graph.id)
                        {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}