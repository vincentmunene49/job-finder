package com.example.jobfinder.user.apply.presentation

import android.net.Uri

sealed class JobApplicationEvent {
    data class OnTypeName(val name: String) : JobApplicationEvent()
    data class OnTypeEmail(val email: String) : JobApplicationEvent()
    data class OnClickCvAttachment(val cvAttachmentUri: Uri?, val cvByteArray: ByteArray?) : JobApplicationEvent()

    data class OnClickCoverLetter(val coverLetterUri: Uri?, val coverLetterByteArray: ByteArray?) : JobApplicationEvent()

    data class OnTypePhoneNumber(val phoneNumber: String) : JobApplicationEvent()

    object OnClickApply : JobApplicationEvent()
}