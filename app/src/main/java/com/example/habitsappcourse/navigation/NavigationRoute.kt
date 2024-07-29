package com.example.habitsappcourse.navigation

sealed class NavigationRoute(val route: String){
    object Onboarding : NavigationRoute("onboarding")
    object Login : NavigationRoute("login")
    object Home : NavigationRoute("home")
    object Signup : NavigationRoute("signup")
    object Detail : NavigationRoute("detail")
    object Settings : NavigationRoute("settings")
}
