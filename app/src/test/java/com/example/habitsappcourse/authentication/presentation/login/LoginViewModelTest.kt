package com.example.habitsappcourse.authentication.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.authentication_domain.usecase.LoginWhitEmailUseCase
import com.example.authentication_domain.usecase.PaswordResult
import com.example.authentication_domain.usecase.ValidateEmailUseCase
import com.example.authentication_domain.usecase.ValidatePasswordUseCase
import com.example.authentication_presentation.util.PaswordErrorParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val testDispatcher = Dispatchers.IO

    @Mock
    lateinit var validateEmailUseCase: com.example.authentication_domain.usecase.ValidateEmailUseCase

    @Mock
    lateinit var validatePasswordUseCase: com.example.authentication_domain.usecase.ValidatePasswordUseCase

    @Mock
    lateinit var loginWhitEmailUseCase: com.example.authentication_domain.usecase.LoginWhitEmailUseCase

    @Mock
    lateinit var paswordErrorParser: com.example.authentication_presentation.util.PaswordErrorParser

    private lateinit var viewModel: com.example.authentication_presentation.login.LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = com.example.authentication_presentation.login.LoginViewModel(
            validateEmailUseCase,
            validatePasswordUseCase,
            loginWhitEmailUseCase
        )
        paswordErrorParser = com.example.authentication_presentation.util.PaswordErrorParser
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `email changed updates state`() {
        val email = "test@example.com"
        viewModel.onEvent(com.example.authentication_presentation.login.LoginEvent.EmailChanged(email))
        assertEquals(email, viewModel.state.email)
    }

    @Test
    fun `password changed updates state`() {
        val password = "Password1"
        viewModel.onEvent(com.example.authentication_presentation.login.LoginEvent.PasswordChanged(password))

        assertEquals(password, viewModel.state.password)
    }

    @Test
    fun `invalid email sets emailError`() {
        whenever(validateEmailUseCase.invoke(any())).thenReturn(false)
        whenever(validatePasswordUseCase.invoke(any())).thenReturn(com.example.authentication_domain.usecase.PaswordResult.VALID)
        viewModel.onEvent(com.example.authentication_presentation.login.LoginEvent.Login)

        assertEquals("The email is not valid", viewModel.state.emailError)
    }

    @Test
    fun `invalid password sets passwordError`() {
        whenever(validateEmailUseCase.invoke(any())).thenReturn(true)
        whenever(validatePasswordUseCase.invoke(any())).thenReturn(com.example.authentication_domain.usecase.PaswordResult.INVALID_LENGTH)

        viewModel.onEvent(com.example.authentication_presentation.login.LoginEvent.Login)

        assertEquals("Password must be at least 8 characters", viewModel.state.passwordError)
    }

    @Test
    fun `valid credentials triggers login`() = runTest {
        whenever(validateEmailUseCase.invoke(any())).thenReturn(true)
        whenever(validatePasswordUseCase.invoke(any())).thenReturn(com.example.authentication_domain.usecase.PaswordResult.VALID)
        whenever(loginWhitEmailUseCase.invoke(any(), any())).thenReturn(Result.success(Unit))

        viewModel.onEvent(com.example.authentication_presentation.login.LoginEvent.Login)

        assertTrue(viewModel.state.isLoading)
        advanceUntilIdle()
        assertFalse(viewModel.state.isLoading)
        assertTrue(viewModel.state.isLoggedIn)
    }

    @Test
    fun `login failure sets passwordError`() = runTest {
        val errorMessage = "Login failed"
        whenever(validateEmailUseCase.invoke(any())).thenReturn(true)
        whenever(validatePasswordUseCase.invoke(any())).thenReturn(com.example.authentication_domain.usecase.PaswordResult.VALID)
        whenever(loginWhitEmailUseCase.invoke(any(), any())).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.onEvent(com.example.authentication_presentation.login.LoginEvent.Login)

        assertTrue(viewModel.state.isLoading)
        advanceUntilIdle()
        assertEquals(errorMessage, viewModel.state.passwordError)
    }
}