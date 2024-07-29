package com.example.authentication_domain.usecase

class ValidatePasswordUseCase () {
    operator fun invoke(password: String): PaswordResult {
        if(password.length < 8)
        {
            return PaswordResult.INVALID_LENGTH
        }
        if(!password.any { it.isLowerCase() })
        {
            return PaswordResult.INVALID_LOWERCASE
        }
        if(!password.any { it.isUpperCase() })
        {
            return PaswordResult.INVALID_UPPERCASE
        }
        if(!password.any { it.isDigit() })
        {
            return PaswordResult.INVALID_DIGITS
        }
        return PaswordResult.VALID

    }
}

enum class PaswordResult {
    VALID,
    INVALID_LOWERCASE,
    INVALID_UPPERCASE,
    INVALID_DIGITS,
    INVALID_LENGTH
}