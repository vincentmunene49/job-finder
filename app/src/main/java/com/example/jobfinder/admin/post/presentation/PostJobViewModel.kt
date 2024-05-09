package com.example.jobfinder.admin.post.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.admin.post.data.model.Job
import com.example.jobfinder.admin.post.domain.PostJobRepository
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.common.util.validator.FormValidatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PostJobViewModel @Inject constructor(
    private val postJobRepository: PostJobRepository,
    private val formValidatorRepository: FormValidatorRepository
) : ViewModel() {

    var state by mutableStateOf(JobPostingState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: JobPostingEvent) {
        when (event) {
            is JobPostingEvent.OnTypeEmail -> {
                state = state.copy(companyEmail = event.email)
                validateEmail()
            }

            is JobPostingEvent.OnTypeJobTitle -> {
                state = state.copy(jobTitle = event.jobTitle)
                validateGeneralField(event.jobTitle, "jobTitle")
            }

            is JobPostingEvent.OnTypeJobDescription -> {
                state = state.copy(jobDescription = event.jobDescription)
                validateGeneralField(event.jobDescription, "jobDescription")
            }

            is JobPostingEvent.OnTypeJobCity -> {
                state = state.copy(jobCity = event.jobCity)
                validateGeneralField(event.jobCity, "jobCity")
            }

            is JobPostingEvent.OnTypeJobCountry -> {
                state = state.copy(jobCountry = event.jobCountry)
                validateGeneralField(event.jobCountry, "jobCountry")
            }

            JobPostingEvent.OnClickPostJob -> {

                if (validateEmail() && validateGeneralField(state.jobTitle ?: "", "jobTitle") &&
                    validateGeneralField(state.jobDescription ?: "", "jobDescription") &&
                    validateGeneralField(state.jobCity ?: "", "jobCity") &&
                    validateGeneralField(state.jobCountry ?: "", "jobCountry") &&
                    validateGeneralField(state.companyEmail ?: "", "companyEmail") &&
                    validateGeneralField(
                        state.requirements?.joinToString(separator = ", ") ?: "",
                        "requirements"
                    )
                ) {
                    postJob()
                }

            }

            JobPostingEvent.OnClickJobLevel -> {
                state = state.copy(isJobLevelExpanded = !state.isJobLevelExpanded!!)
            }

            JobPostingEvent.OnClickJobType -> {
                state = state.copy(isJobTypeExpanded = !state.isJobTypeExpanded!!)

            }

            JobPostingEvent.OnClickCurrency -> {
                state = state.copy(isCurrencyExpanded = !state.isCurrencyExpanded!!)

            }

            JobPostingEvent.OnClickSalary -> {
                state = state.copy(isSalaryExpanded = !state.isSalaryExpanded!!)
            }

            JobPostingEvent.OnClickFrequency -> {
                state = state.copy(isFrequencyExpanded = !state.isFrequencyExpanded!!)
            }

            JobPostingEvent.OnClickWorkingModel -> {
                state = state.copy(isWorkingModelExpanded = !state.isWorkingModelExpanded!!)
            }

            is JobPostingEvent.OnClickAddRequirement -> {
                val newRequirement = state.requirements
                newRequirement?.add(event.requirement)
            }

            JobPostingEvent.OnDismissCurrency -> {
                state = state.copy(isCurrencyExpanded = false)
            }

            JobPostingEvent.OnDismissFrequency -> {
                state = state.copy(isFrequencyExpanded = false)
            }

            JobPostingEvent.OnDismissJobLevel -> {
                state = state.copy(isJobLevelExpanded = false)
            }

            JobPostingEvent.OnDismissJobType -> {
                state = state.copy(isJobTypeExpanded = false)
            }

            JobPostingEvent.OnDismissSalary -> {
                state = state.copy(isSalaryExpanded = false)
            }

            JobPostingEvent.OnDismissWorkingModel -> {
                state = state.copy(isWorkingModelExpanded = false)
            }

            is JobPostingEvent.OnSelectCurrency -> {
                state = state.copy(
                    currency = event.currency,
                    isCurrencyExpanded = false
                )
            }

            is JobPostingEvent.OnSelectFrequency -> {
                state = state.copy(
                    frequency = event.frequency,
                    isFrequencyExpanded = false
                )
            }

            is JobPostingEvent.OnSelectJobLevel -> {
                state = state.copy(
                    jobLevel = event.level,
                    isJobLevelExpanded = false
                )
            }

            is JobPostingEvent.OnSelectJobType -> {
                state = state.copy(
                    jobType = event.jobType,
                    isJobTypeExpanded = false
                )
            }

            is JobPostingEvent.OnSelectSalary -> {
                state = state.copy(
                    salary = event.salary,
                    isSalaryExpanded = false
                )
            }

            is JobPostingEvent.OnSelectWorkingModel -> {
                state = state.copy(
                    workingModel = event.workingModel,
                    isWorkingModelExpanded = false
                )
            }

            is JobPostingEvent.OnPhotoSelected -> {
                state = state.copy(
                    companyLogo = event.byteArray,
                    companyLogoUri = event.uri
                )
            }

            is JobPostingEvent.OnAddRequirement -> {
                val newRequirements = state.requirements
                newRequirements?.set(event.index, event.requirement)
                state = state.copy(requirements = newRequirements)

            }
        }
    }

    private fun postJob() {
        val job = Job(
            companyId = UUID.randomUUID().toString(),
            jobTitle = state.jobTitle?.lowercase() ?: "",
            jobDescription = state.jobDescription?.lowercase() ?: "",
            workingModel = state.workingModel ?: "",
            jobType = state.jobType ?: "",
            currency = state.currency ?: "",
            frequency = state.frequency ?: "",
            salary = state.salary ?: "",
            date = state.date,
            level = state.jobLevel ?: "",
            jobLocation = "${state.jobCity?.lowercase()}, ${state.jobCountry?.lowercase()}",
            companyEmail = state.companyEmail?.lowercase() ?: "",
            requirements = state.requirements?.map { it.lowercase() } ?: mutableListOf()
        )
        viewModelScope.launch {
            postJobRepository.postJob(
                job = job,
                jobImage = state.companyLogo ?: byteArrayOf()
            ).onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true,
                            jobTitle = "",
                            jobDescription = "",
                            jobCity = "",
                            jobCountry = "",
                            companyEmail = "",
                            requirements = mutableListOf(),
                            jobLevel = "",
                            jobType = "",
                            currency = "",
                            salary = "",
                            frequency = "",
                            workingModel = "",
                            companyLogo = byteArrayOf(),
                            companyLogoUri = null

                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(isLoading = false)
                        _uiEvent.send(UiEvent.OnSuccess("Job posted successfully"))
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                    }

                    else -> {}
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun validateEmail(): Boolean {
        val emailResult = formValidatorRepository.validateEmail(state.companyEmail ?: "")
        state = state.copy(companyEmailError = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validateGeneralField(field: String, fieldName: String): Boolean {
        val result = formValidatorRepository.validateGeneralStringField(field)
        state = when (fieldName) {
            "jobTitle" -> state.copy(jobTitleError = result.errorMessage)
            "jobDescription" -> state.copy(jobDescriptionError = result.errorMessage)
            "jobCity" -> state.copy(jobCityError = result.errorMessage)
            "jobCountry" -> state.copy(jobCountryError = result.errorMessage)
            "companyEmail" -> state.copy(companyEmailError = result.errorMessage)
            "requirements" -> state.copy(requirementsError = result.errorMessage)
            else -> state
        }
        return result.successful
    }
}