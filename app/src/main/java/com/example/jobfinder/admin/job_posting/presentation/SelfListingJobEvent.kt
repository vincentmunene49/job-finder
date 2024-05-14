package com.example.jobfinder.admin.job_posting.presentation

sealed class SelfListingJobEvent {
    data class OnClickJobItem(val jobId: String) : SelfListingJobEvent()

    data class OnClickDeleteJob(val jobId: String) : SelfListingJobEvent()

    object OnDismissDialog : SelfListingJobEvent()


}