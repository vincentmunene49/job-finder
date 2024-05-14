package com.example.jobfinder.admin.job_posting.presentation

import com.example.jobfinder.admin.post.data.model.Job

data class SelfJobListingState(
    val isLoading: Boolean = false,
    val jobs: List<Job> = emptyList(),
    val error: String = "",
    val jobId:String? = "",
    val showActionDialog:Boolean = false
) {
}