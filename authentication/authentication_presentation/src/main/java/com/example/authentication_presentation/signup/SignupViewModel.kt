package com.example.authentication_presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authentication_domain.usecase.SignupWhitEmailUseCase
import com.example.authentication_domain.usecase.ValidateEmailUseCase
import com.example.authentication_domain.usecase.ValidatePasswordUseCase
import com.example.authentication_presentation.util.PaswordErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val signupWhitEmailUseCase: SignupWhitEmailUseCase
) : ViewModel() {

    var state by mutableStateOf(SignupSate())
        private set

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }

            is SignupEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            SignupEvent.logIn -> {
                state = state.copy(logIn = true)
            }

            SignupEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
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
                signupWhitEmailUseCase(
                    email = state.email,
                    password = state.password
                ).onSuccess {
                    state = state.copy(
                        isSignedIn = true
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