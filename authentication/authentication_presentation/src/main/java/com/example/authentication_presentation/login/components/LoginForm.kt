package com.example.authentication_presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.authentication_presentation.login.LoginEvent
import com.example.authentication_presentation.login.LoginState
import com.example.core_presentation.HabitButton
import com.example.core_presentation.HabitPasswordTextfield
import com.example.core_presentation.HabitTextfield

@Composable
fun LoginForm(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {

    val focusManager = LocalFocusManager.current

    Box(modifier = modifier, contentAlignment = Alignment.Center)
    {
        Column(
            modifier = modifier.background(Color.White, shape = RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login whit Email",
                modifier = Modifier.padding(20.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
            )
            com.example.core_presentation.HabitTextfield(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
                placeholder = "Email",
                contentDescription = "Enter Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
                    .padding(horizontal = 20.dp),
                leadingIcon = Icons.Outlined.Email,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onAny = {
                    focusManager.moveFocus(
                        FocusDirection.Next
                    )
                }),
                errorMessage = state.emailError,
                isEnabled = !state.isLoading
            )
            com.example.core_presentation.HabitPasswordTextfield(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
                contentDescription = "Enter Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)
                    .padding(horizontal = 20.dp),
                errorMessage = state.passwordError,
                isEnabled = !state.isLoading,
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
                text = "login", modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp), isEnabled = !state.isLoading
            ) {
                onEvent(LoginEvent.Login)
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Forgot password?",
                    color = MaterialTheme.colorScheme.tertiary,
                    textDecoration = TextDecoration.Underline
                )
            }
            TextButton(onClick = onSignUp) {
                Text(
                    text = buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)){
                            append("Sign up")
                        }
                    },
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        if(state.isLoading)
        {
            CircularProgressIndicator()
        }
    }
}