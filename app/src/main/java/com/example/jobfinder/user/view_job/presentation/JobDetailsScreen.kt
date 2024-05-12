package com.example.jobfinder.user.view_job.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.user.home.common.OrgIcon
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.ui.theme.JobFinderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun JobDetailsScreen(
    navHostController: NavController,
    fromHomeScreen: Boolean,
    viewModel: JobDetailsViewModel
) {
    JobDescriptionScreenContent(
        navHostController = navHostController,
        fromHomeScreen = fromHomeScreen,
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        uiEvent = viewModel.uiEvent
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun JobDescriptionScreenContent(
    modifier: Modifier = Modifier,
    navHostController: NavController,
    fromHomeScreen: Boolean = true,
    state: JobDetailState,
    onEvent: (ViewJobEvent) -> Unit = {},
    uiEvent: Flow<UiEvent>
) {
    val scrollState = rememberLazyListState()
    val scrollOffset = remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset

        }
    }
    val context = LocalContext.current

//    LaunchedEffect(key1 = true) {
//        uiEvent.collect {
//            when (it) {
//                is UiEvent.OnSuccess -> {
//                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                }
//
//                else -> {}
//            }
//        }
//    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigateUp() }) {
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
                    enabled = state.isButtonEnabled,
                    onClick = {
                        if (fromHomeScreen) {
                            navHostController.navigate(route = Routes.Apply.route + "/${state.jobItem.jobId}" + "/${state.jobItem.jobPosterId}")
                        } else {
                            onEvent(ViewJobEvent.OnClickWithdrawApplication)
                            navHostController.navigateUp()
                        }
                    }) {
                    Text(
                        text = if (fromHomeScreen) "Apply" else "Withdraw Application",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column() {
                AnimatedVisibility(visible = scrollOffset.value == 0) {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            HeaderSection(
                                jobTitle = state.jobItem.jobTitle,
                                companyName = state.jobItem.companyName ?: "",
                                companyLogo = state.jobItem.companyLogo,
                                location = state.jobItem.jobLocation ?: "",
                                email = state.jobItem.companyEmail,
                                phone = "+1 123 456 7890"
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            JobActionBox(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp),
                                icon = Icons.Default.Money,
                                action = "Salary",
                                description = "${state.jobItem.currency} ${state.jobItem.salary}/${state.jobItem.frequency}"
                            )
                            JobActionBox(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp),
                                icon = Icons.Default.HomeRepairService,
                                action = "Job Type",
                                description = state.jobItem.jobType
                            )

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            JobActionBox(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp),
                                icon = Icons.Default.Business,
                                action = "Working Model",
                                description = state.jobItem.workingModel
                            )
                            JobActionBox(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp),
                                icon = Icons.Default.BarChart,
                                action = "Level",
                                description = state.jobItem.level
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    state = scrollState
                ) {
                    stickyHeader {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.background),
                            text = "About this job",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),

                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = state.jobItem.jobDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    stickyHeader {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.background),

                            text = "Job Description",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),

                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                    }



                    items(state.jobItem.requirements) {

                        JobDescriptionHolder(
                            Modifier.padding(vertical = 5.dp),
                            jobDescription = it,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                }


            }

            if (state.isLoading) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}

@Composable
fun JobDescriptionHolder(
    modifier: Modifier = Modifier,
    jobDescription: String,
    color: Color
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(2.dp),
                color = color
            )
            Text(
                text = jobDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

        }
    }
}

@Composable
fun HeaderSection(
    jobTitle: String,
    companyName: String,
    companyLogo: String?,
    modifier: Modifier = Modifier,
    location: String,
    email: String,
    phone: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OrgIcon(
            orgIcon = companyLogo,
            defaultIcon = R.drawable.google
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = jobTitle,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface

        )
        Text(
            text = companyName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = phone,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }

}

@Composable
fun JobActionBox(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    action: String,
    description: String
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .size(40.dp),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = action,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
        }

    }

}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewJobDescription() {
    JobFinderTheme {
        JobDescriptionScreenContent(
            state = JobDetailState(),
            navHostController = rememberNavController(),
            fromHomeScreen = true,
            uiEvent = flowOf(),
        )
    }
}