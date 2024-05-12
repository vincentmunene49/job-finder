package com.example.jobfinder.user.applications.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.common.data.model.JobItem
import com.example.jobfinder.common.presentation.EmptyComponent
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.user.home.presentation.JobCard
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.ui.theme.JobFinderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.joda.time.DateTime
import org.joda.time.Days

@Composable
fun ApplicationListScreen(
    navHostController: NavController,
    viewModel: ApplicationScreenViewModel = hiltViewModel()
) {
    ApplicationListScreenContent(
        navHostController = navHostController,
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        uiEvent = viewModel.uiEvent

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationListScreenContent(
    navHostController: NavController,
    state: ApplicationsScreenState,
    onEvent: (ApplicationsEvent) -> Unit,
    uiEvent: Flow<UiEvent>
) {

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
                        text = "Your Applications",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {

                items(state.applications ?: emptyList()) { job ->
                    JobCard(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        orgIcon = job.companyLogo,
                        city = job.jobLocation,
                        jobTitle = job.jobTitle,
                        companyName = job.companyName ?: "",
                        country = "",
                        currency = job.currency,
                        frequency = job.frequency,
                        salary = job.salary,
                        days = Days.daysBetween(
                            DateTime(job.date),
                            DateTime.now()
                        ).days.toLong(),
                        openStatus = true,
                        onClick = {
                            navHostController.navigate(Routes.JobDetails.route + "/${job.jobId}")
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

            }
            if (state.isLoading) {
                LoadingAnimation(modifier = Modifier.align(Alignment.Center))
            }

            if(state.applications.isNullOrEmpty() && !state.isLoading){
                EmptyComponent(message = "No Jobs Applied")
            }

        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewHomeScreen() {
    JobFinderTheme {
        ApplicationListScreenContent(
            rememberNavController(),
            state = ApplicationsScreenState(),
            onEvent = {},
            uiEvent = flowOf()
        )
    }

}