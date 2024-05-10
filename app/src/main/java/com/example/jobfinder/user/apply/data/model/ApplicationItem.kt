package com.example.jobfinder.user.apply.data.model

data class ApplicationItem(
    val jobId: String? = "",
    val userId:String? = "",
    val name: String? = "",
    val email: String? = "",
    val cvAttachment: String? = "",
    val coverLetter: String? = "",
    val phoneNumber: String? = "",
) {
}