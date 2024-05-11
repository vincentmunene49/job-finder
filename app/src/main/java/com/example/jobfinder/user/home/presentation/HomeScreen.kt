package com.example.jobfinder.user.home.presentation

import android.content.res.Configuration
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.navigation.Routes
import com.example.jobfinder.ui.theme.JobFinderTheme
import com.example.jobfinder.user.home.common.OrgIcon
import org.joda.time.DateTime
import org.joda.time.Days

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreenContent(
        navController = navController,
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    navController: NavController,
    state: HomeState,
    onEvent: (HomeEvent) -> Unit
) {

    Scaffold(
        topBar = {
            MediumTopAppBar(title = { /*TODO*/ })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues = paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Search For Jobs",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchBoxComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 16.dp),
                        value = state.search ?: "",
                        onValueChange = {
                            onEvent(HomeEvent.OnTypeSearchValue(it))
                        },
                        onClickClear = {
                            onEvent(HomeEvent.OnClickClear)
                        }
                    )

                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                onEvent(HomeEvent.OnClickClear)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.padding(5.dp),
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                    }
                }

                Divider(modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp))


                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.jobs ?: emptyList()) { job ->
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
                                DateTime.now()).days.toLong(),
                            openStatus = job.openStatus,
                            onClick = {
                                navController.navigate(Routes.JobDetails.route + "/${job.jobId}")
                            }
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

@Composable
fun SearchBoxComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClickClear: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
        enabled = true,
        readOnly = false,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()

            }
        ),
        decorationBox = { innerTextField ->

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    innerTextField()
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            onClickClear()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Cancel,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }

        }

    )

}


@Composable
fun JobCard(
    modifier: Modifier = Modifier,
    orgIcon: String?,
    city: String,
    jobTitle: String,
    companyName: String,
    country: String,
    currency: String = "",
    frequency: String = "",
    salary: String,
    days: Long,
    openStatus: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OrgIcon(
                orgIcon = orgIcon,
                defaultIcon = R.drawable.google
            )

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
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "$companyName . $city",
                    color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
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
            Column {
                Text(
                    text = "$days days",
                    color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = if (openStatus) "Open" else "Closed",
                    color = if (openStatus) Color.Green else Color.Red,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
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
        HomeScreenContent(
            navController = rememberNavController(),
            state = HomeState(),
            onEvent = {})
    }

}