package com.example.authentication_presentation.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.authentication_presentation.R
import com.example.authentication_presentation.signup.components.SignupForm

@Composable
fun SignupScreen(
    onSignIn: () -> Unit,
    onLogIn: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {

    val state = viewModel.state

    LaunchedEffect(state.isSignedIn) {
        if(state.isSignedIn)
        {
            onSignIn()
        }
    }

    LaunchedEffect(state.logIn) {
        if (state.logIn) {
            onLogIn()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(painterResource(id = R.drawable.signup), contentDescription = "sign up")
        SignupForm(state = state, onEvent = viewModel::onEvent)
    }

    if(state.isLoading)
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            CircularProgressIndicator()
        }
    }

}