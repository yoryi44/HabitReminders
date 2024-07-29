package com.example.authentication_presentation.signup

sealed interface SignupEvent {
    data class EmailChanged(val email: String) : SignupEvent
    data class PasswordChanged(val password: String) : SignupEvent
    object logIn : SignupEvent
    object SignUp : SignupEvent
}