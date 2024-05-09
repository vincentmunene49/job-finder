package com.example.jobfinder.admin.post.presentation

import android.net.Uri

sealed class JobPostingEvent {
    data class OnClickAddRequirement(val requirement: String) : JobPostingEvent()
    data class OnTypeEmail(val email: String) : JobPostingEvent()

    data class OnTypeJobTitle(val jobTitle: String) : JobPostingEvent()

    data class OnTypeJobDescription(val jobDescription: String) : JobPostingEvent()

    data class OnTypeJobCity(val jobCity: String) : JobPostingEvent()

    data class OnTypeJobCountry(val jobCountry: String) : JobPostingEvent()
    data class OnTypeCompanyName(val companyName: String) : JobPostingEvent()
    object OnClickPostJob : JobPostingEvent()

    object OnClickJobLevel : JobPostingEvent()

    object OnClickJobType : JobPostingEvent()

    object OnClickCurrency : JobPostingEvent()

    object OnClickSalary : JobPostingEvent()

    object OnClickFrequency : JobPostingEvent()


    object OnClickWorkingModel : JobPostingEvent()

    object OnDismissJobLevel : JobPostingEvent()

    object OnDismissJobType : JobPostingEvent()

    object OnDismissCurrency : JobPostingEvent()

    object OnDismissSalary : JobPostingEvent()

    object OnDismissFrequency : JobPostingEvent()

    object OnDismissWorkingModel : JobPostingEvent()

    data class OnSelectWorkingModel(val workingModel: String) : JobPostingEvent()

    data class OnSelectJobType(val jobType: String) : JobPostingEvent()

    data class OnSelectJobLevel(val level: String) : JobPostingEvent()

    data class OnSelectCurrency(val currency: String) : JobPostingEvent()

    data class OnSelectSalary(val salary: String) : JobPostingEvent()

    data class OnSelectFrequency(val frequency: String) : JobPostingEvent()
    data class OnPhotoSelected(val uri: Uri?, val byteArray: ByteArray?) : JobPostingEvent()


    data class OnAddRequirement(val index: Int, val requirement: String) : JobPostingEvent()


}