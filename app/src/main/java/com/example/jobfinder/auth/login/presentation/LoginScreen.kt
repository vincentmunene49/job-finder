package com.example.jobfinder.auth.login.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.R
import com.example.jobfinder.auth.common.AuthInput
import com.example.jobfinder.common.presentation.JobFinderAppButton
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.ui.theme.JobFinderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@Composable
fun LoginScreen(
    navHostController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreenContent(
        navHostController = navHostController,
        state = viewModel.state,
        onEvent = viewModel::event,
        uiEvent = viewModel.uiEvent
    )
}

@Composable
fun LoginScreenContent(
    navHostController: NavController,
    state: LoginState,
    onEvent: (LoginEvents) -> Unit,
    uiEvent: Flow<UiEvent>,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateToAdminScreens -> {
                    navHostController.navigate(
                        route = Routes.MainAdminScreens.route
                    ) {
                        popUpTo(Routes.Login.route) {
                            inclusive = true
                        }

                    }
                    Timber.tag("LoginScreen").d("Navigate to Admin Screens")
                }

                is UiEvent.NavigateToUserScreens -> {
                    navHostController.navigate(
                        route = Routes.MainUserScreens.route
                    ) {
                        popUpTo(Routes.Login.route) {
                            inclusive = true
                        }

                    }
                }

                else -> {}
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Login",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Welcome Back",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.titleMedium
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.loginimage),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AuthInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                icon = Icons.Default.Email,
                label = "Email",
                input = state.email,
                supportingText = state.emailErrorMessage ?: "",
                errorMessage = state.emailErrorMessage,
                onInput = {
                    onEvent(LoginEvents.OnTypeEmail(it))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                )
            )

            AuthInput(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                icon = Icons.Default.Lock,
                label = "Password",
                input = state.password,
                supportingText = state.passwordErrorMessage ?: "",
                errorMessage = state.passwordErrorMessage,
                onInput = {
                    onEvent(LoginEvents.OnTypePassword(it))
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )

            JobFinderAppButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = {
                    onEvent(LoginEvents.OnClickLogin)
                },
                text = "Login"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account?",
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    modifier = Modifier.clickable {
                        navHostController.navigate(Routes.Register.route)
                    },
                    text = "Sign Up",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }

        if (state.isLoading) {
            LoadingAnimation(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSignUpScreen() {
    JobFinderTheme {
        LoginScreenContent(
            rememberNavController(),
            state = LoginState(),
            onEvent = {},
            uiEvent = flowOf()
        )
    }

}