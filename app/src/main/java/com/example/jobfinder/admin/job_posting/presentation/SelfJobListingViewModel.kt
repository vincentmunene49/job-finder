package com.example.jobfinder.admin.job_posting.presentation

import Resource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.admin.job_posting.domain.SelfJobListingRepo
import com.example.jobfinder.common.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelfJobListingViewModel @Inject constructor(
    private val repo: SelfJobListingRepo
) : ViewModel() {

    var state by mutableStateOf(SelfJobListingState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getJobs()
    }

    fun onEvent(onEvent: SelfListingJobEvent) {
        when (onEvent) {
            is SelfListingJobEvent.OnClickDeleteJob -> {
                deleteJob(onEvent.jobId)
                state = state.copy(
                    showActionDialog = false
                )
            }

            is SelfListingJobEvent.OnClickJobItem -> {
                state = state.copy(
                    showActionDialog = true,
                    jobId = onEvent.jobId
                )
            }

            SelfListingJobEvent.OnDismissDialog -> {
                state = state.copy(
                    showActionDialog = false
                )

            }
        }
    }


    private fun getJobs() {
        viewModelScope.launch {
            repo.getJobs().onEach {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            jobs = it.data ?: emptyList()
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred"
                        )
                    }

                    else -> {}
                }
            }.launchIn(this)

        }
    }

    private fun deleteJob(jobId: String) {
        viewModelScope.launch {
            repo.deleteJob(jobId).onEach {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        )
                        _uiEvent.send(UiEvent.OnSuccess("Job Deleted"))
                        getJobs()
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred"
                        )
                    }

                    else -> {}
                }
            }.launchIn(this)
        }
    }
}