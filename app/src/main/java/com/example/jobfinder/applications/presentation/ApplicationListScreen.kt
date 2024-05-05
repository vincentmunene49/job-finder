package com.example.jobfinder.applications.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.common.data.model.JobItem
import com.example.jobfinder.home.presentation.JobCard
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.ui.theme.JobFinderTheme

@Composable
fun ApplicationListScreen(
    navHostController: NavController
) {
    ApplicationListScreenContent(navHostController = navHostController, applications = emptyList())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationListScreenContent(
    applications: List<JobItem>,
    navHostController: NavController
) {

    Scaffold(
        modifier = androidx.compose.ui.Modifier
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
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            items(10) {
                JobCard(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    orgIcon = null,
                    city = "Lagos",
                    jobTitle = "Software Engineer",
                    companyName = "Google",
                    country = "Nigeria",
                    salary = "$100,000",
                    days = "2",
                    openStatus = false,
                    onClick = {
                        navHostController.navigate(Routes.JobDetails.route)
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
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
            emptyList(),
            rememberNavController()
        )
    }

}