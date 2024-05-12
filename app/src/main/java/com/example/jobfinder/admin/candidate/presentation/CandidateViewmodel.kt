package com.example.jobfinder.admin.candidate.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.admin.candidate.domain.CandidateRepo
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.common.util.broadcast.DownloadCompletedReceiver
import com.example.jobfinder.common.util.downloader.Downloader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CandidateViewmodel @Inject constructor(
    private val repository: CandidateRepo,
    private val downloader: Downloader
) : ViewModel() {

    var state by mutableStateOf(CandidateState())
        private set

    private var lastInitializedId: String? = null

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun initialize(id: String) {
        if (id != lastInitializedId) {
            getCandidateDetails(id)
            lastInitializedId = id
        }
    }

    fun onEvent(event: CandidateEvent) {
        when (event) {
            is CandidateEvent.OnClickDownloadCv -> {
                downloadFile(
                    event.attachment,
                    "${state.candidateDetails?.applicationItem?.name} cv"
                )
                if(!DownloadCompletedReceiver.completed){
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.OnCvDownLoadComplete("Download Started"))

                    }
                }
            }

            is CandidateEvent.OnClickDownloadCoverLetter -> {
                downloadFile(
                    event.attachment,
                    "${state.candidateDetails?.applicationItem?.name} cover-letter"
                )
                if(!DownloadCompletedReceiver.completed){
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.OnCoverLetterDownLoadComplete("Download Started"))


                    }
                }
            }
        }
    }

    private fun getCandidateDetails(
        jobId: String
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

    private fun downloadFile(url: String, name: String) {
        downloader.downloadFile(url, name)

    }
}