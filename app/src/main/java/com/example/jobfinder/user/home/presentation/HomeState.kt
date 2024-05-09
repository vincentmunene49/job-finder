package com.example.jobfinder.user.home.presentation

import com.example.jobfinder.admin.post.data.model.Job

data class HomeState(
    val isLoading: Boolean? = false,
    val jobs: List<Job>? = emptyList(),
    val error: String? = "",
    val search: String? = ""
) {
}