package com.example.jobfinder.auth.sign_up.presentation

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.R
import com.example.jobfinder.auth.common.AuthInput
import com.example.jobfinder.common.presentation.ErrorDialog
import com.example.jobfinder.common.presentation.JobFinderAppButton
import com.example.jobfinder.common.presentation.JobFinderTextInput
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.ui.theme.JobFinderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SignUpScreen(
    navHostController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    SignUpScreenContent(
        navHostController,
        state = viewModel.state,
        onEvent = viewModel::event,
        uiEvent = viewModel.uiEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreenContent(
    navHostController: NavController,
    state: SignUpState,
    onEvent: (SignUpEvents) -> Unit,
    uiEvent: Flow<UiEvent>,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = { },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        LaunchedEffect(key1 = true) {
            uiEvent.collect { event ->
                when (event) {
                    is UiEvent.OnSuccess -> {
                        navHostController.navigate(route = Routes.Login.route) {
                            popUpTo(Routes.Register.route) {
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
                .padding(paddingValues = paddingValues)
        ) {


            Column(
                modifier = Modifier
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Sign Up",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Join us to find your dream job",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 8.dp),
                    text = "choose your account type",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AccountTypeCard(
                        icon = R.drawable.boss,
                        accountTypeText = "Employer",
                        isSelected = state.isAdmin,
                        onClick = {
                            onEvent(SignUpEvents.OnClickAdmin)
                        }
                    )
                    AccountTypeCard(
                        icon = R.drawable.jobseeker,
                        accountTypeText = "Job Finder",
                        isSelected = !state.isAdmin,
                        onClick = {
                            onEvent(SignUpEvents.OnClickUser)
                        }
                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                AuthInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    icon = Icons.Default.Email,
                    label = "Email",
                    input = state.email,
                    supportingText = state.emailErrorMessage ?: "",
                    onInput = {
                        onEvent(SignUpEvents.OnTypeEmail(it))
                    },
                    errorMessage = state.emailErrorMessage,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AuthInput(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Person,
                        label = "First Name",
                        input = state.firstName,
                        errorMessage = state.firstNameErrorMessage,
                        supportingText = state.firstNameErrorMessage ?: "",
                        onInput = {
                            onEvent(SignUpEvents.OnTypeFirstName(it))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    AuthInput(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Person,
                        label = "Last Name",
                        input = state.lastName,
                        errorMessage = state.lastNameErrorMessage,
                        supportingText = state.lastNameErrorMessage ?: "",
                        onInput = {
                            onEvent(SignUpEvents.OnTypeLastName(it))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Down) }
                        )
                    )

                }
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
                        onEvent(SignUpEvents.OnTypePassword(it))
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
                        onEvent(SignUpEvents.OnClickRegister)
                    },
                    text = "Sign Up"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account?",
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        modifier = Modifier.clickable {
                            navHostController.navigate(Routes.Login.route)
                        },
                        text = "Sign In",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            if (state.isLoading) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            if (state.showErrorDialog) {
                ErrorDialog(
                    errorMessage = state.errorMessage,
                    onDismissDialog = { onEvent(SignUpEvents.OnDismissErrorDialog) })
            }

        }

    }
}


@Composable
fun AccountTypeCard(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    isSelected: Boolean = false,
    accountTypeText: String,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = modifier
            .size(width = 120.dp, height = 130.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(5.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
            .clickable { onClick() }
            .clip(RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(id = icon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = accountTypeText,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                if (isSelected) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }

        }

    }

}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSignUpScreen() {
    JobFinderTheme {
        SignUpScreenContent(
            rememberNavController(),
            state = SignUpState(),
            onEvent = {},
            uiEvent = flowOf()
        )
    }

}