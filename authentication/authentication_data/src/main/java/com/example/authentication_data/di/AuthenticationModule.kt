package com.example.authentication_data.di

import com.example.authentication_data.matcher.EmailMatcherImpl
import com.example.authentication_data.repository.AuthenticationRepositoryImpl
import com.example.authentication_domain.matcher.EmailMatcher
import com.example.authentication_domain.repository.AuthenticationRepository
import com.example.authentication_domain.usecase.GetUserIdUseCase
import com.example.authentication_domain.usecase.LoginWhitEmailUseCase
import com.example.authentication_domain.usecase.LogoutUseCase
import com.example.authentication_domain.usecase.SignupWhitEmailUseCase
import com.example.authentication_domain.usecase.ValidateEmailUseCase
import com.example.authentication_domain.usecase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository() : AuthenticationRepository
    {
        return AuthenticationRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideEmailMatcher() : EmailMatcher
    {
        return EmailMatcherImpl()
    }

    @Provides
    @Singleton
    fun provideValidateEmailUseCase(emailMatcher: EmailMatcher) : ValidateEmailUseCase
    {
        return ValidateEmailUseCase(emailMatcher)
    }

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase() : ValidatePasswordUseCase
    {
        return ValidatePasswordUseCase()
    }

    @Provides
    @Singleton
    fun provideLoginWhitEmailUseCase(authenticationRepository: AuthenticationRepository) : LoginWhitEmailUseCase
    {
        return LoginWhitEmailUseCase(authenticationRepository)
    }

    @Provides
    @Singleton
    fun provideSignupWhitEmailUseCase(authenticationRepository: AuthenticationRepository) : SignupWhitEmailUseCase
    {
        return SignupWhitEmailUseCase(authenticationRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserIdUseCase(authenticationRepository: AuthenticationRepository) : GetUserIdUseCase
    {
        return GetUserIdUseCase(authenticationRepository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(authenticationRepository: AuthenticationRepository) : LogoutUseCase
    {
        return LogoutUseCase(authenticationRepository)
    }

}