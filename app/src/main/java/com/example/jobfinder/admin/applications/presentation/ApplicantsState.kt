package com.example.jobfinder.admin.applications.presentation

import com.example.jobfinder.admin.applications.data.model.ApplicationJobItem

data class ApplicantsState(
    val applicants: List<ApplicationJobItem>? = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
) {
}