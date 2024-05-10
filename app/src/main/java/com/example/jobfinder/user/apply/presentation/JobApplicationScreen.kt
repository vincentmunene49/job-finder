package com.example.jobfinder.user.apply.presentation

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.example.jobfinder.admin.post.presentation.JobPostingEvent
import com.example.jobfinder.common.presentation.AppBasicTextField
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.ui.theme.JobFinderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf

@Composable
fun JobApplicationScreen(
    navController: NavController,
    viewModel: JobApplicationViewModel
) {
    JobApplicationScreenContent(
        navController = navController,
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        uiEvent = viewModel.uiEvent

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobApplicationScreenContent(
    navController: NavController,
    state: JobApplicationState,
    onEvent: (JobApplicationEvent) -> Unit,
    uiEvent: Flow<UiEvent>
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val cvAttachmentPicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            val byteArray: ByteArray? = uri?.let {
                context.contentResolver.openInputStream(it)?.use { inputStream ->
                    inputStream.readBytes()
                }
            }
            onEvent(
                JobApplicationEvent.OnClickCvAttachment(
                    cvByteArray = byteArray,
                    cvAttachmentUri = uri
                )
            )
        }

    val coverLetterPicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            val byteArray: ByteArray? = uri?.let {
                context.contentResolver.openInputStream(it)?.use { inputStream ->
                    inputStream.readBytes()
                }
            }
            onEvent(
                JobApplicationEvent.OnClickCoverLetter(
                    coverLetterByteArray = byteArray,
                    coverLetterUri = uri
                )
            )
        }

    LaunchedEffect(key1 = true) {
        uiEvent.collect {
            when (it) {
                is UiEvent.OnSuccess -> {
                    navController.navigate(Routes.Success.route)
                }

                else -> {}
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Apply for Job",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.graphicsLayer {
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp
                    )
                    clip = true
                }
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = CircleShape,
                    onClick = {
                        onEvent(JobApplicationEvent.OnClickApply)
                    }) {
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Apply",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier

                    .padding(horizontal = 18.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = "Full Name*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppBasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.name ?: "",
                    onValueChange = {
                        onEvent(JobApplicationEvent.OnTypeName(it))
                    },
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    hint = "John Doe"
                )

                Spacer(modifier = Modifier.height(8.dp))
                if (state.nameError != null) {
                    Text(
                        text = state.nameError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Email*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppBasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.email ?: "",
                    onValueChange = {
                        onEvent(JobApplicationEvent.OnTypeEmail(it))
                    },
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    hint = "johndoe@gmail.com"
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.emailError != null) {
                    Text(
                        text = state.emailError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Phone*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppBasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.phoneNumber ?: "",
                    onValueChange = {
                        onEvent(JobApplicationEvent.OnTypePhoneNumber(it))
                    },
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    hint = "+254 712 345 678"
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.phoneNumberError != null) {
                    Text(
                        text = state.phoneNumberError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Upload CV/Resume*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            cvAttachmentPicker.launch("*/*")
                        }
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {
                        Icon(
                            imageVector = if (state.cvUri.toString() == "null"
                            ) Icons.Default.UploadFile else Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = if (state.cvUri.toString() == "null"
                            ) MaterialTheme.colorScheme.primary else Color.Green.copy(
                                alpha = 0.5f
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (state.cvUri.toString() == "null") "" else state.cvUri.toString(),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))
                if (state.cvAttachmentError != null) {
                    Text(
                        text = state.cvAttachmentError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Cover Letter(Optional)",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            coverLetterPicker.launch("*/*")
                        }
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Icon(
                            imageVector = if (state.coverLetter.toString() == "null"
                            ) Icons.Default.UploadFile else Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = if (state.coverLetterUri.toString() == "null"
                            ) MaterialTheme.colorScheme.primary else Color.Green.copy(
                                alpha = 0.5f
                            ),
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (state.coverLetterUri.toString() == "null") "" else state.coverLetterUri.toString(),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            if (state.isLoading == true) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewJobApplicationScreen() {
    JobFinderTheme {
        JobApplicationScreenContent(
            rememberNavController(),
            state = JobApplicationState(),
            onEvent = {},
            uiEvent = flowOf()
        )
    }
}