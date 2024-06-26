package com.example.jobfinder.admin.post.data.model

data class Job(
    val jobId: String? = "",
    val companyId: String = "",
    val jobTitle: String = "",
    val jobDescription: String = "",
    val workingModel: String = "",
    val jobType: String = "",
    val currency: String = "",
    val frequency: String = "",
    val salary: String = "",
    val date: Long = 0L,
    val jobPosterId: String = "",
    val openStatus:Boolean = false,
    val level: String = "",
    val jobLocation: String = "",
    val companyLogo: String? = "",
    val companyName: String? = "",
    val companyEmail: String = "",
    val requirements: List<String> = emptyList()
) {


}