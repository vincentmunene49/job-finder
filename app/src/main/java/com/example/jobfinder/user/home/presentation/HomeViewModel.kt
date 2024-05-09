package com.example.jobfinder.user.home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.user.home.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        getAllJobs()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnClickJobItem -> {}

            HomeEvent.OnClickClear -> {
                Timber.tag("HomeViewModel").d("onClickClear: clear clicked, state: $state")

                state = state.copy(
                    search = "",
                )
                Timber.tag("HomeViewModel").d("onClickClear: clear clicked, state: $state")
                getAllJobs()
            }

            is HomeEvent.OnTypeSearchValue -> {
                state = state.copy(
                    search = event.search,
                )
                searchJobs(event.search)

            }
        }
    }


    private fun getAllJobs() {
        viewModelScope.launch {
            repository.getAllJobs().onEach {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            jobs = it.data ?: emptyList(),
                            isLoading = false
                        )
                        Timber.tag("HomeViewMode").d("getAllJobs: ${it.data}")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred"
                        )

                        Timber.tag("HomeViewModel").d("getAllJobsError: ${it.message}")
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun searchJobs(jobTitle: String) {
        viewModelScope.launch {
            repository.searchJobs(jobTitle).onEach {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(jobs = it.data ?: emptyList(), isLoading = false)
                        Timber.tag("HomeViewModel").d("searchJobs: ${it.data}")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = it.message ?: "An unexpected error occurred",
                            isLoading = false
                        )
                        Timber.tag("HomeViewModel").d("searchJobsError: ${it.message}")
                    }

                    else -> {}
                }
            }.launchIn(this)
        }
    }
}