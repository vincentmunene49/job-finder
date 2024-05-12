package com.example.jobfinder.user.applications.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.user.applications.domain.GetJobApplications
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ApplicationScreenViewModel @Inject constructor(
    private val repository: GetJobApplications
) : ViewModel() {

    var state by mutableStateOf(ApplicationsScreenState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getJobsAppliedFor()
    }

    fun onEvent(event: ApplicationsEvent) {
        when (event) {
            is ApplicationsEvent.OnClickJobItem -> {

            }
        }
    }

    private fun getJobsAppliedFor() {
        viewModelScope.launch {
            repository.getJobsAppliedFor()
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            state = state.copy(
                                applications = result.data ?: emptyList(),
                                isLoading = false
                            )

                            Timber.tag("ApplicationScreenViewModel").d("Applications: ${state.applications}")
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message ?: "An unexpected error occurred",
                                isLoading = false
                            )
                        }

                        else -> {}
                    }
                }
                .launchIn(viewModelScope)
        }
    }


}