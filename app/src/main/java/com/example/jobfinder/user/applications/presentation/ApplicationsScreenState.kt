package com.example.jobfinder.user.applications.presentation

import com.example.jobfinder.admin.post.data.model.Job

data class ApplicationsScreenState(
    val isLoading: Boolean = false,
    val applications: List<Job>? = emptyList(),
    val error: String = ""
) {
}