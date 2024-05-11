package com.example.jobfinder.user.applications.presentation

sealed class ApplicationsEvent {
    data class OnClickJobItem(val jobId: String) : ApplicationsEvent()
}