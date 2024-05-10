package com.example.jobfinder.user.apply.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.common.util.validator.FormValidatorRepository
import com.example.jobfinder.user.apply.data.model.ApplicationItem
import com.example.jobfinder.user.apply.domain.ApplicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobApplicationViewModel @Inject constructor(
    private val repository: ApplicationRepository,
    private val formValidatorRepository: FormValidatorRepository
) : ViewModel() {

    fun init(jobId: String) {
        state = state.copy(jobId = jobId)
    }

    var state by mutableStateOf(JobApplicationState())
        private set


    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: JobApplicationEvent) {
        when (event) {
            is JobApplicationEvent.OnTypeName -> {
                state = state.copy(name = event.name)
                validateGeneralField(event.name, "name")
            }

            is JobApplicationEvent.OnTypeEmail -> {
                state = state.copy(email = event.email)
                validateEmail()
            }

            is JobApplicationEvent.OnTypePhoneNumber -> {
                state = state.copy(phoneNumber = event.phoneNumber)
                validatePhoneNumber()
            }

            is JobApplicationEvent.OnClickCoverLetter -> {
                state = state.copy(coverLetterUri = event.coverLetterUri, coverLetter = event.coverLetterByteArray)
            }

            is JobApplicationEvent.OnClickCvAttachment -> {
                state = state.copy(cvUri = event.cvAttachmentUri, cvAttachment = event.cvByteArray)

                validateGeneralField(event.cvAttachmentUri.toString(), "cvAttachment")
            }

            is JobApplicationEvent.OnClickApply -> {
                if (validateEmail() && validateGeneralField(
                        state.name ?: "",
                        "name"
                    ) && validatePhoneNumber() && validateGeneralField(
                        state.cvUri.toString(),
                        "cvAttachment"
                    )
                ) {
                    postJob()
                }
            }
        }
    }

    private fun postJob() {
        val applicationItem = ApplicationItem(
            name = state.name,
            email = state.email,
            phoneNumber = state.phoneNumber,
            jobId = state.jobId
        )
        val cvAttachment = state.cvAttachment ?: byteArrayOf()
        val coverLetter = state.coverLetter ?: byteArrayOf()
        viewModelScope.launch {
            repository.applyForJob(
                applicationItem,
                cvAttachment = cvAttachment,
                coverLetter = coverLetter
            ).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            name = "",
                            email = "",
                            phoneNumber = "",
                            cvUri = null,
                            coverLetterUri = null
                            )
                        _uiEvent.send(UiEvent.OnSuccess("Application submitted successfully"))
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)

        }

    }

    private fun validateEmail(): Boolean {
        val emailResult = formValidatorRepository.validateEmail(state.email ?: "")
        state = state.copy(emailError = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validateGeneralField(field: String, fieldName: String): Boolean {
        val result = formValidatorRepository.validateGeneralStringField(field)
        state = when (fieldName) {
            "name" -> state.copy(nameError = result.errorMessage)
            "cvAttachment" -> state.copy(cvAttachmentError = result.errorMessage)

            else -> state
        }
        return result.successful
    }

    private fun validatePhoneNumber(): Boolean {
        val result = formValidatorRepository.validatePhoneNumber(state.phoneNumber ?: "")
        state = state.copy(phoneNumberError = result.errorMessage)
        return result.successful
    }
}