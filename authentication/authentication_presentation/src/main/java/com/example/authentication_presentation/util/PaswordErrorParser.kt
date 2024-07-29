package com.example.authentication_presentation.util

import com.example.authentication_domain.usecase.PaswordResult

object PaswordErrorParser {
    fun parseError(error: PaswordResult): String? {
        return when(error) {
            PaswordResult.VALID -> null
            PaswordResult.INVALID_LOWERCASE -> "Password must contain at least one lowercase letter"
            PaswordResult.INVALID_UPPERCASE -> "Password must contain at least one uppercase letter"
            PaswordResult.INVALID_DIGITS -> "Password must contain at least one digit"
            PaswordResult.INVALID_LENGTH -> "Password must be at least 8 characters"
        }
    }
}