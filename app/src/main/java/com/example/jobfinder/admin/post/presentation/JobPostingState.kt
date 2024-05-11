package com.example.jobfinder.admin.post.presentation

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class JobPostingState(
    val jobTitleError: String? = null,
    val companyNameError: String? = null,
    val companyLogo:ByteArray? = null,
    val companyLogoUri: Uri? = null,
    val error:String? = null,
    val jobDescriptionError: String? = null,
    val jobCityError: String? = null,
    val jobCountryError: String? = null,
    val companyEmailError: String? = null,
    val requirementsError: String? = null,
    val isDataValid: Boolean? = false,
    val isLoading: Boolean? = false,
    val jobTitle: String? = "",
    val companyName:String? = "",
    val jobDescription: String? = "",
    val jobCity: String? = "",
    val jobCountry: String? = "",
    val companyEmail: String? = "",
    val requirements: MutableList<String>? = mutableStateListOf(),
    val jobLevel:String? = "",
    val jobType:String? = "",
    val currency:String? = "",
    val salary:String? = "",
    val frequency: String? = "",
    val workingModel:String? = "",
    val isJobLevelExpanded:Boolean? = false,
    val isJobTypeExpanded:Boolean? = false,
    val isCurrencyExpanded:Boolean? = false,
    val isSalaryExpanded:Boolean? = false,
    val isFrequencyExpanded:Boolean? = false,
    val isWorkingModelExpanded:Boolean? = false,
    val date:Long = DateTime().millis


) {
}