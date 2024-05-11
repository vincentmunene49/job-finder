package com.example.jobfinder.admin.applications.data.model

import User
import com.example.jobfinder.admin.post.data.model.Job
import com.example.jobfinder.user.apply.data.model.ApplicationItem

data class ApplicationJobItem(
    val user:User? = User(),
    val applicationItem: ApplicationItem? = ApplicationItem(),
    var job: Job? = null
) {
}