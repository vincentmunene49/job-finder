package com.example.jobfinder.user.apply.presentation

import android.app.AlertDialog
import android.net.Uri

data class JobApplicationState (
    val isLoading: Boolean? = false,
    val error: String? = "",
    val name: String? = "",
    val email: String? = "",
    val emailError: String? = "",
    val nameError: String? = "",
    val phoneNumberError: String? = "",
    val cvAttachment: ByteArray? = null,
    val cvAttachmentError: String? = "",
    val coverLetter: ByteArray? = null,
    val isButtonEnable: Boolean? = true,
    val showAlreadyAppliedDialog: Boolean? = false,
    val coverLetterUri: Uri? = null,
    val cvUri: Uri? = null,
    val phoneNumber: String? = "",
    val jobId: String? = "",
    val jobPosterId: String? = "",
) {
}