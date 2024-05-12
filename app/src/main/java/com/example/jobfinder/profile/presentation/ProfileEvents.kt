package com.example.jobfinder.profile.presentation

import android.net.Uri

sealed class ProfileEvents {

    object OnClickLogOut : ProfileEvents()

    data class OnClickImage (val uri: Uri?, val byteArray: ByteArray?): ProfileEvents()
}