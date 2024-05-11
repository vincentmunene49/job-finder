package com.example.jobfinder.admin.candidate.presentation

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.ui.theme.JobFinderTheme

@Composable
fun CandidateApplicationScreen(
    navController: NavController,
    viewModel: CandidateViewmodel
) {
    CandidateApplicationScreenContent(
        navController = navController,
        state = viewModel.state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidateApplicationScreenContent(
    navController: NavController,
    state: CandidateState
) {
    val verticalScroll = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = "Applicant Details",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(paddingValues)

        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScroll)
            ) {

                DetailsHeader(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    image = state.candidateDetails?.user?.imagePath ?: "",
                    name = state.candidateDetails?.applicationItem?.name ?: "",
                    email = state.candidateDetails?.applicationItem?.email ?: "",
                    phoneNumber = state.candidateDetails?.applicationItem?.phoneNumber ?: ""
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Post Applied For : ",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = state.candidateDetails?.job?.jobTitle ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Level: ",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                    Text(
                        text = state.candidateDetails?.job?.level ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                    )

                }

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "CV Attachment",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp)
                        .padding(horizontal = 16.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { }
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Default.PictureAsPdf,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    IconButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomEnd),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.padding(4.dp),
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }


                }

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    text = "Cover-Letter Attachment",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(150.dp)
                        .padding(horizontal = 16.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { }
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    Icon(
                        modifier = Modifier.align(Alignment.Center),
                        imageVector = Icons.Default.PictureAsPdf,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    IconButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.BottomEnd),
                        onClick = { /*TODO*/ }) {
                        Icon(
                            modifier = Modifier.padding(4.dp),
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }


                }


            }
            if (state.isLoading) {
                LoadingAnimation(modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}


@Composable
fun DetailsHeader(
    modifier: Modifier = Modifier,
    image: String,
    name: String,
    email: String,
    phoneNumber: String
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = CircleShape
                    )
                    .size(70.dp)
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

            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = email,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = phoneNumber,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onPrimary
            )


        }

    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCandidateApplication() {
    JobFinderTheme {
        CandidateApplicationScreenContent(
            rememberNavController(),
            CandidateState()
        )
    }
}