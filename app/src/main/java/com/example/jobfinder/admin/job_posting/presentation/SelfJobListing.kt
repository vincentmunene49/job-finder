package com.example.jobfinder.admin.job_posting.presentation

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.EmptyComponent
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.user.home.common.OrgIcon
import com.example.jobfinder.user.home.presentation.JobCard
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime
import org.joda.time.Days

@Composable
fun SelfJobListingScreen(
    viewModel: SelfJobListingViewModel = hiltViewModel()
) {

    SelfJobListingScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        uiEvent = viewModel.uiEvent
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfJobListingScreenContent(
    state: SelfJobListingState,
    onEvent: (SelfListingJobEvent) -> Unit,
    uiEvent: Flow<UiEvent>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        uiEvent.collect {
            when (it) {
                is UiEvent.OnSuccess -> {
                    Toast.makeText(context, "Job Deleted", Toast.LENGTH_SHORT).show()
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
                        text = "Your Job Posting",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(state.jobs) { jobItem ->
                    JobCardInList(
                        jobTitle = jobItem.jobTitle,
                        salary = jobItem.salary,
                        frequency = jobItem.frequency,
                        onClick = {
                            onEvent(SelfListingJobEvent.OnClickJobItem(jobItem.jobId ?: ""))
                        }
                    )

                }


            }

            if (state.showActionDialog) {
                DeleteJobDialog(
                    modifier = Modifier.align(Alignment.Center),
                    onDismissDialog = {
                        onEvent(SelfListingJobEvent.OnDismissDialog)
                    },
                    onConfirm = {
                        onEvent(SelfListingJobEvent.OnClickDeleteJob(state.jobId ?: ""))
                    }
                )
            }

            if (state.isLoading) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (state.jobs.isNullOrEmpty()){
                EmptyComponent(message = "No Jobs Posted")

            }

        }

    }

}

@Composable
private fun JobCardInList(
    modifier: Modifier = Modifier,
    jobTitle: String,
    currency: String = "",
    frequency: String = "",
    salary: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = jobTitle,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = currency,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = salary,
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = " / $frequency",
                        color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                    )
                }


            }

        }

    }
}

@Composable
fun DeleteJobDialog(
    modifier: Modifier = Modifier,
    onDismissDialog: () -> Unit = {},
    onConfirm: () -> Unit = {},

) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismissDialog()
        },
        title = {
            Text(
                text = "Delete Job Posting",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Text(
                "Would Like to remove this job from you job listing?",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal)
            )
        },
        confirmButton = {
            Button(

                onClick = {
                    onConfirm()
                }) {
                Text("Delete")
            }
        },
        dismissButton = {
            Button(

                onClick = {
                    onDismissDialog()
                }) {
                Text("Cancel")
            }
        }
    )
}





