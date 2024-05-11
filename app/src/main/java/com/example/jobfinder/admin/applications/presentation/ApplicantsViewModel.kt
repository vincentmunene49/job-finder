package com.example.jobfinder.admin.applications.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.admin.applications.domain.ApplicantsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantsViewModel @Inject constructor(
    private val repo: ApplicantsRepo
) : ViewModel() {

    var state by mutableStateOf(ApplicantsState())
        private set

    init {

        getJobApplicants()
    }


    private fun getJobApplicants() {
        viewModelScope.launch {
            repo.getJobApplicants().onEach {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            applicants = it.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = it.message ?: "",
                            isLoading = false
                        )
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

}