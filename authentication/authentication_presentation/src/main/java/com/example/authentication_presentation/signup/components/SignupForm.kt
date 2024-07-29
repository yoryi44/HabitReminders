package com.example.authentication_presentation.signup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.authentication_presentation.signup.SignupEvent
import com.example.authentication_presentation.signup.SignupSate
import com.example.core_presentation.HabitButton
import com.example.core_presentation.HabitPasswordTextfield
import com.example.core_presentation.HabitTextfield
import com.example.core_presentation.HabitTitle

@Composable
fun SignupForm(
    state: SignupSate,
    onEvent: (SignupEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusManager = LocalFocusManager.current

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        com.example.core_presentation.HabitTitle(title = "Create your account")
        Spacer(modifier = modifier.size(32.dp))
        com.example.core_presentation.HabitTextfield(
            value = state.email,
            onValueChange = { onEvent(SignupEvent.EmailChanged(it)) },
            placeholder = "Email",
            contentDescription = "Enter Email",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            leadingIcon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false, keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.moveFocus(
                    FocusDirection.Next
                )
            }),
            errorMessage = state.emailError,
            isEnabled = !state.isLoading,
            backgroundColor = Color.White
        )
        com.example.core_presentation.HabitPasswordTextfield(
            value = state.password,
            onValueChange = { onEvent(SignupEvent.PasswordChanged(it)) },
            contentDescription = "Enter Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            errorMessage = state.passwordError,
            isEnabled = !state.isLoading, backgroundColor = Color.White,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onAny = {
                focusManager.clearFocus()
            })
        )
        com.example.core_presentation.HabitButton(
            text = "Create Account", modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp), isEnabled = !state.isLoading
        ) {
            onEvent(SignupEvent.SignUp)
        }
        TextButton(onClick = { onEvent(SignupEvent.logIn) }) {
            Text(
                text = buildAnnotatedString {
                    append("Already have an account? ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)){
                        append("Sign in")
                    }
                },
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}