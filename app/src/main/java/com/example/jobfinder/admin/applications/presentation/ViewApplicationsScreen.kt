package com.example.jobfinder.admin.applications.presentation

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.EmptyAnimation
import com.example.jobfinder.common.presentation.EmptyComponent
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.ui.theme.JobFinderTheme

@Composable
fun ViewApplicationsScreen(
    navController: NavController,
    viewModel: ApplicantsViewModel = hiltViewModel()
) {
    ViewApplicationsScreenContent(
        navController = navController,
        state = viewModel.state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewApplicationsScreenContent(
    navController: NavController,
    state: ApplicantsState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Applications",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        )
        {
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.applicants ?: emptyList()) { applicant ->
                    ProfileHolder(
                        image = applicant.user?.imagePath ?: "",
                        name = applicant.applicationItem?.name ?: "",
                        email = applicant.applicationItem?.email ?: "",
                        actionText = "View Profile",
                        onClick = {
                            navController.navigate(route = Routes.Applicant.route + "/${applicant.applicationItem?.id}")
                        }
                    )
                }
            }

            if(state.isLoading){
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if(state.applicants.isNullOrEmpty() && !state.isLoading){
                EmptyComponent(
                    "No Applicants Currently"
                )
            }
        }
    }

}

@Composable
fun ProfileHolder(
    image: String,
    name: String,
    email: String,
    actionText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
            .clickable { onClick() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
                    model = image,
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.person)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = actionText,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }


}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewViewApplications() {
    JobFinderTheme {
        ViewApplicationsScreenContent(
            rememberNavController(),
            state = ApplicantsState()
        )
    }
}