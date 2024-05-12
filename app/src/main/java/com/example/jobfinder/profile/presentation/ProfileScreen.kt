package com.example.jobfinder.profile.presentation

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.user.applications.presentation.ApplicationListScreenContent
import com.example.jobfinder.ui.theme.JobFinderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        uiEvent = viewModel.uiEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreenContent(
    state: ProfileState,
    onEvent: (ProfileEvents) -> Unit,
    uiEvent: Flow<UiEvent>
) {

    LaunchedEffect(key1 = true) {
        uiEvent.collect {
            when (it) {
                is UiEvent.NavigateToLoginScreen -> {
                }

                else -> {}
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Profile",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(5.dp)
                        )

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    shape = CircleShape
                                )
                                .size(50.dp)
                                .clickable { }
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center

                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .padding(8.dp),
                                model = state.profileData.imagePath,
                                contentDescription = null,
                                placeholder = painterResource(id = R.drawable.person)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))

                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {

                            Text(
                                text = state.profileData.firstName + " " + state.profileData.lastName,
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = state.profileData.email ?: "",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(CircleShape)
//                        .clickable {
//                            onEvent(ProfileEvents.OnClickLogOut)
//                        },
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.Logout,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.primary
//                    )
//
//                    Text(
//                        text = "Logout",
//                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
//                        color = MaterialTheme.colorScheme.primary,
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }

            }
            if (state.isLoading) {
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
fun PreviewHomeScreen() {
    JobFinderTheme {
        ProfileScreenContent(
            state = ProfileState(),
            onEvent = {},
            uiEvent = flowOf()
        )
    }

}
