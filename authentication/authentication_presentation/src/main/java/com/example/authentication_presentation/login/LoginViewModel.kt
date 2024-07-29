package com.example.authentication_presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication_domain.usecase.LoginWhitEmailUseCase
import com.example.authentication_domain.usecase.ValidateEmailUseCase
import com.example.authentication_domain.usecase.ValidatePasswordUseCase
import com.example.authentication_presentation.util.PaswordErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginWhitEmailUseCase: LoginWhitEmailUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is LoginEvent.Login -> {
                login()
            }

            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

        }
    }

    private fun login() {

        state = state.copy(
            emailError = null,
            passwordError = null
        )

        if (!validateEmailUseCase(state.email)) {
            state = state.copy(
                emailError = "The email is not valid"
            )
        }

        val passwordResult = validatePasswordUseCase(state.password)
        state = state.copy(
            passwordError = PaswordErrorParser.parseError(passwordResult)
        )


        if (state.emailError == null && state.passwordError == null) {

            state = state.copy(
                isLoading = true
            )

            viewModelScope.launch {
                loginWhitEmailUseCase(
                    email = state.email,
                    password = state.password
                ).onSuccess {
                    state = state.copy(
                        isLoggedIn = true
                    )
                }.onFailure {
                    state = state.copy(
                        passwordError = it.message
                    )
                }
                state = state.copy(
                    isLoading = false
                )
            }
        }
    }
}