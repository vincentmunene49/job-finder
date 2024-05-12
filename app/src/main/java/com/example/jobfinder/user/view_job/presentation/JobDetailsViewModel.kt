package com.example.jobfinder.user.view_job.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.user.view_job.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var state by mutableStateOf(JobDetailState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun initJobFunction(jobId: String) {
        getJob(jobId)
    }

    fun onEvent(event: ViewJobEvent) {
        when (event) {
            is ViewJobEvent.OnClickWithdrawApplication -> {
                withdrawApplication()
            }
        }
    }

    private fun getJob(jobId: String) {
        viewModelScope.launch {
            repository.getJobById(jobId).onEach {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            jobItem = it.data!!,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = it.message!!,
                            isLoading = false
                        )
                    }

                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun withdrawApplication() {
        viewModelScope.launch {
            repository.withdrawApplication().onEach {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        )
                        _uiEvent.send(UiEvent.OnSuccess("Application withdrawn successfully"))
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = it.message!!,
                            isLoading = false
                        )
                    }

                    else -> {}
                }
            }.launchIn(this)
        }
    }
}