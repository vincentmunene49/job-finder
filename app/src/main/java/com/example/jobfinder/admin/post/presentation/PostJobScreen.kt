package com.example.jobfinder.admin.post.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.AppBasicTextField
import com.example.jobfinder.common.presentation.JobFinderAppButton
import com.example.jobfinder.ui.theme.JobFinderTheme

@Composable
fun PostJobScreen() {
    PostJobScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostJobScreenContent() {
    val verticalScroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Create New Job Listing",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(verticalScroll)
                .padding(horizontal = 16.dp)
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.4f),
                            shape = CircleShape
                        )
                        .size(80.dp)
                        .clickable { }
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center

                ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(8.dp),
                        model = null,
                        contentDescription = "<a href=\"https://www.freepik.com/free-psd/engraved-black-logo-mockup_9866103.htm#query=logo&position=42&from_view=keyword&track=sph&uuid=cd8a1d8b-ba7c-4b51-9e70-7b9be1d388a8\">Image by Vectonauta</a> on Freepik",
                        contentScale = ContentScale.Fit,
                        placeholder = painterResource(id = R.drawable.baseline_camera_24)
                    )


                }

            }

            Text(
                text = "Job Title*",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppBasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                hint = "Software Developer"
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Contact Email*",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppBasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                hint = "info@info.com"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Requirements*",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            AppBasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = {},
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                hint = "Bsc. in Computer Science"
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { }
                    .clip(CircleShape),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Box(
                    modifier =
                    Modifier
                        .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                        .size(30.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Text(text = "Add Requirement")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "About*",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppBasicTextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                value = "",
                boxPadding = 20.dp,
                onValueChange = {},
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                hint = "We are a company that specializes in software development...."
            )
            Spacer(modifier = Modifier.height(16.dp))
            JobFinderAppButton(onClick = { /*TODO*/ }, text = "Post Job")
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PostJobScreenContentPreview() {
    JobFinderTheme {
        PostJobScreenContent()
    }
}