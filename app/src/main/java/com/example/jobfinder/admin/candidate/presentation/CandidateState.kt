package com.example.jobfinder.admin.candidate.presentation

import com.example.jobfinder.admin.applications.data.model.ApplicationJobItem

data class CandidateState(
    val isLoading:Boolean = false,
    val candidateDetails: ApplicationJobItem? = null,
    val applicationItemId: String? = null,
) {
}