package com.example.jobfinder.admin.post.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.AppBasicTextField
import com.example.jobfinder.common.presentation.JobFinderAppButton
import com.example.jobfinder.common.presentation.LoadingAnimation
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.common.util.enums.Currency
import com.example.jobfinder.common.util.enums.Frequency
import com.example.jobfinder.common.util.enums.JobType
import com.example.jobfinder.common.util.enums.Level
import com.example.jobfinder.common.util.enums.SalaryRange
import com.example.jobfinder.common.util.enums.WorkingModel
import com.example.jobfinder.ui.theme.JobFinderTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun PostJobScreen(
    viewModel: PostJobViewModel = hiltViewModel()
) {
    PostJobScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        uiEvent = viewModel.uiEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostJobScreenContent(
    state: JobPostingState,
    onEvent: (JobPostingEvent) -> Unit,
    uiEvent: Flow<UiEvent>
) {
    val verticalScroll = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        uiEvent.collect {
            when (it) {
                is UiEvent.OnSuccess -> {
                    Toast.makeText(context, "Job Posted Successfully", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    val imagePicker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            val byteArray: ByteArray? = uri?.let {
                context.contentResolver.openInputStream(it)?.use { inputStream ->
                    inputStream.readBytes()
                }
            }
            onEvent(JobPostingEvent.OnPhotoSelected(uri, byteArray))
        }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(verticalScroll)
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)

                            .background(
                                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.4f),
                                shape = CircleShape
                            )

                            .clickable {
                                imagePicker.launch("image/*")
                            }
                            .size(120.dp),
                        contentAlignment = Alignment.Center

                    ) {
                        if (mutableStateOf(state.companyLogoUri).value == null) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_camera_24),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            AsyncImage(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(120.dp),
                                model = state.companyLogoUri,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )


                        }
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
                    value = state.jobTitle ?: "",
                    onValueChange = {
                        onEvent(JobPostingEvent.OnTypeJobTitle(it))
                    },
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    hint = "Software Developer"
                )

                Spacer(modifier = Modifier.height(8.dp))
                if (state.jobTitleError != null) {
                    Text(
                        text = state.jobTitleError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Contact Email*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppBasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.companyEmail ?: "",
                    onValueChange = {
                        onEvent(JobPostingEvent.OnTypeEmail(it))
                    },
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    hint = "info@info.com"
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.companyEmailError != null) {
                    Text(
                        text = state.companyEmailError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Country*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppBasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.jobCountry ?: "",
                    onValueChange = {
                        onEvent(JobPostingEvent.OnTypeJobCountry(it))
                    },
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    hint = "Kenya"
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.jobCountryError != null) {
                    Text(
                        text = state.jobCountryError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "City*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppBasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.jobCity ?: "",
                    onValueChange = {
                        onEvent(JobPostingEvent.OnTypeJobCity(it))
                    },
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    hint = "Nairobi"
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.jobCityError != null) {
                    Text(
                        text = state.jobCityError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Working Model*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                DropDownChoiceComponent(
                    text = state.workingModel ?: WorkingModel.REMOTE.name.lowercase(),
                    dropDownMenuItems = listOf(
                        WorkingModel.REMOTE.name,
                        WorkingModel.ONSITE.name,
                        WorkingModel.HYBRID.name
                    ),
                    isExpanded = state.isWorkingModelExpanded ?: false,
                    onExpandedChange = {
                        onEvent(JobPostingEvent.OnClickWorkingModel)
                    },
                    onDismiss = {
                        onEvent(JobPostingEvent.OnDismissWorkingModel)
                    },
                    onClick = {
                        onEvent(JobPostingEvent.OnSelectWorkingModel(it))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "Job Type*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                DropDownChoiceComponent(
                    text = state.jobType ?: JobType.INTERNSHIP.name.lowercase(),
                    dropDownMenuItems = listOf(
                        JobType.FULL_TIME.name,
                        JobType.PART_TIME.name,
                        JobType.INTERNSHIP.name,
                        JobType.CONTRACT.name
                    ),
                    isExpanded = state.isJobTypeExpanded ?: false,
                    onExpandedChange = {
                        onEvent(JobPostingEvent.OnClickJobType)
                    },
                    onDismiss = {
                        onEvent(JobPostingEvent.OnDismissJobType)
                    },
                    onClick = {
                        onEvent(JobPostingEvent.OnSelectJobType(it))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Salary Range*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DropDownChoiceComponent(
                        modifier = Modifier.weight(1f),
                        dropDownMenuItems = listOf(
                            Currency.KSH.name,
                            Currency.USD.name,
                            Currency.EUR.name
                        ),
                        text = state.currency ?: Currency.KSH.name.lowercase(),
                        onDismiss = {
                            onEvent(JobPostingEvent.OnDismissCurrency)
                        },
                        isExpanded = state.isCurrencyExpanded ?: false,
                        onExpandedChange = {
                            onEvent(JobPostingEvent.OnClickCurrency)
                        },
                        onClick = {
                            onEvent(JobPostingEvent.OnSelectCurrency(it))
                        }
                    )
                    DropDownChoiceComponent(
                        modifier = Modifier.weight(2f),
                        dropDownMenuItems = listOf(
                            SalaryRange.BETWEEN_1000_AND_100000.value,
                            SalaryRange.ABOVE_100000.value,
                            SalaryRange.LESS_THAN_500.value
                        ),
                        text = state.salary
                            ?: SalaryRange.BETWEEN_1000_AND_100000.value.lowercase(),
                        onDismiss = {
                            onEvent(JobPostingEvent.OnDismissSalary)
                        },
                        isExpanded = state.isSalaryExpanded ?: false,
                        onExpandedChange = {
                            onEvent(JobPostingEvent.OnClickSalary)
                        },
                        onClick = {
                            onEvent(JobPostingEvent.OnSelectSalary(it))
                        }
                    )
                    DropDownChoiceComponent(
                        modifier = Modifier.weight(1f),
                        text = state.frequency ?: Frequency.MO.name.lowercase(),
                        onDismiss = {
                            onEvent(JobPostingEvent.OnDismissFrequency)
                        },
                        isExpanded = state.isFrequencyExpanded ?: false,
                        onExpandedChange = {
                            onEvent(JobPostingEvent.OnClickFrequency)
                        },
                        dropDownMenuItems = listOf(
                            Frequency.MO.name,
                            Frequency.WK.name,
                            Frequency.YR.name
                        ),
                        onClick = {
                            onEvent(JobPostingEvent.OnSelectFrequency(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))



                Text(
                    text = "Level*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                DropDownChoiceComponent(
                    text = state.jobLevel ?: Level.MID.name.lowercase(),
                    dropDownMenuItems = listOf(
                        Level.MID.name,
                        Level.INTERN.name,
                        Level.SENIOR.name,
                        Level.JUNIOR.name
                    ),
                    isExpanded = state.isJobLevelExpanded ?: false,
                    onExpandedChange = {
                        onEvent(JobPostingEvent.OnClickJobLevel)
                    },
                    onDismiss = {
                        onEvent(JobPostingEvent.OnDismissJobLevel)
                    },
                    onClick = {
                        onEvent(JobPostingEvent.OnSelectJobLevel(it))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Requirements*",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))

                state.requirements?.forEachIndexed { index, item ->

                    AppBasicTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = item,
                        onValueChange = {
                            onEvent(
                                JobPostingEvent.OnAddRequirement(
                                    index = index,
                                    requirement = it
                                )
                            )
                        },
                        keyboardActions = KeyboardActions {
                            focusManager.moveFocus(FocusDirection.Down)
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        hint = "Bsc. in Computer Science"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (state.requirementsError != null) {
                    Text(
                        text = state.requirementsError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(JobPostingEvent.OnClickAddRequirement(""))
                        }
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
                    value = state.jobDescription ?: "",
                    boxPadding = 20.dp,
                    onValueChange = {
                        onEvent(JobPostingEvent.OnTypeJobDescription(it))
                    },
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    hint = "We are a company that specializes in software development...."
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (state.jobDescriptionError != null) {
                    Text(
                        text = state.jobDescriptionError,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                JobFinderAppButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        onEvent(JobPostingEvent.OnClickPostJob)
                    }, text = "Post Job"
                )
                Spacer(modifier = Modifier.height(100.dp))

            }
            if (state.isLoading == true) {
                LoadingAnimation(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownChoiceComponent(
    modifier: Modifier = Modifier,
    text: String,
    dropDownMenuItems: List<String>,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    onClick: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = isExpanded,
        onExpandedChange = onExpandedChange
    ) {

        AppBasicTextField(
            modifier = Modifier.menuAnchor(),
            value = text,
            enabled = false,
            onValueChange = {},
            keyboardActions = KeyboardActions(),
            singleLine = true,
            hasTrailIcon = true,
            icon = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
            keyboardOptions = KeyboardOptions(),
            hint = ""
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { onDismiss() })
        {
            dropDownMenuItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item, style = MaterialTheme.typography.bodySmall) },
                    onClick = {
                        onClick(item)
                    }
                )

            }
        }
    }
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun PostJobScreenContentPreview() {
    JobFinderTheme {
        PostJobScreenContent(
            state = JobPostingState(),
            onEvent = {},
            uiEvent = flowOf()
        )
    }
}