package com.example.jobfinder.admin.candidate.presentation

sealed class CandidateEvent {
    data class OnClickDownloadCv(val attachment:String): CandidateEvent()
    data class OnClickDownloadCoverLetter(val attachment:String): CandidateEvent()
}