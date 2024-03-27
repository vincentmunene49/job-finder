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
import com.example.jobfinder.R
import com.example.jobfinder.auth.common.AuthInput
import com.example.jobfinder.common.presentation.JobFinderAppButton
import com.example.jobfinder.ui.theme.JobFinderTheme

@Composable
fun LoginScreen() {
    
}

@Composable
fun LoginScreenContent() {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
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
            contentAlignment = Alignment.Center){
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
            input = "",
            supportingText = "",
            onInput = {},
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
            input = "",
            supportingText = "",
            onInput = {},
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
            onClick = { /*TODO*/ },
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
                modifier = Modifier.clickable {  },
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewSignUpScreen() {
    JobFinderTheme {
        LoginScreenContent()
    }

}