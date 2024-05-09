package com.example.jobfinder.user.view_job.presentation

import com.example.jobfinder.admin.post.data.model.Job

data class JobDetailState(
    val jobItem:Job = Job(),
    val isLoading:Boolean = false,
    val error:String = ""
) {
}