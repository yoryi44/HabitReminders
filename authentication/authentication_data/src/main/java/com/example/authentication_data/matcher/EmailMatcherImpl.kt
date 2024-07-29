package com.example.authentication_data.matcher

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.authentication_domain.matcher.EmailMatcher

class EmailMatcherImpl : EmailMatcher {
    @RequiresApi(Build.VERSION_CODES.FROYO)
    override fun isValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}