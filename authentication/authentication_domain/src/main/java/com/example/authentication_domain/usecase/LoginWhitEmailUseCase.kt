package com.example.authentication_domain.usecase

import com.example.authentication_domain.repository.AuthenticationRepository

class LoginWhitEmailUseCase (
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authenticationRepository.login(email, password)
    }
}