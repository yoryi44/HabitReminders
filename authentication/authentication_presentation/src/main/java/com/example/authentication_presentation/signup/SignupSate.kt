package com.example.authentication_presentation.signup

data class SignupSate(
    val email:String = "",
    val password:String = "",
    val emailError:String? = null,
    val passwordError: String? = null,
    val isSignedIn:Boolean = false,
    val isLoading:Boolean = false,
    val logIn:Boolean = false
)
