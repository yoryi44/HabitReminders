package com.example.habitsappcourse.authentication.domain.usecase

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var validatePasswordUseCase : com.example.authentication_domain.usecase.ValidatePasswordUseCase

    @Before
    fun setUp(){
        validatePasswordUseCase =
            com.example.authentication_domain.usecase.ValidatePasswordUseCase()
    }

    @Test
    fun `password less than 8 characters returns Invalid`() {
        val result = validatePasswordUseCase.invoke("Pass1")
        assertEquals(result, com.example.authentication_domain.usecase.PaswordResult.INVALID_LENGTH)
    }

    @Test
    fun `password without lowercase letter returns Invalid`() {
        val result = validatePasswordUseCase.invoke("PASSWORD1")
        assertEquals(result, com.example.authentication_domain.usecase.PaswordResult.INVALID_LOWERCASE)
    }

    @Test
    fun `password without uppercase letter returns Invalid`() {
        val result = validatePasswordUseCase.invoke("password1")
        assertEquals(result, com.example.authentication_domain.usecase.PaswordResult.INVALID_UPPERCASE)
    }

    @Test
    fun `password without digit returns Invalid`() {
        val result = validatePasswordUseCase.invoke("Password")
        assertEquals(result, com.example.authentication_domain.usecase.PaswordResult.INVALID_DIGITS)
    }

    @Test
    fun `valid password returns Valid`() {
        val result = validatePasswordUseCase.invoke("Password1")
        assertEquals(result, com.example.authentication_domain.usecase.PaswordResult.VALID)
    }
}