package com.example.jobfinder.admin.candidate.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.admin.candidate.domain.CandidateRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CandidateViewmodel @Inject constructor(
    private val repository: CandidateRepo
) : ViewModel() {

    var state by mutableStateOf(CandidateState())
        private set

    private var lastInitializedId: String? = null

    fun initialize(id: String) {
        if (id != lastInitializedId) {
            getCandidateDetails(id)
            lastInitializedId = id
        }
    }
    private fun getCandidateDetails(
        jobId:String
    ) {
        viewModelScope.launch {
            repository.getCandidateDetails(jobId)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                candidateDetails = result.data
                            )
                            Timber.tag("CandidateViewmodel").d(result.data.toString())
                        }

                        is Resource.Error -> {
                            state = state.copy(isLoading = false)
                            Timber.tag("CandidateViewmodel").e(result.message ?: "")
                        }

                        else -> {}
                    }
                }.launchIn(viewModelScope)
        }
    }
}