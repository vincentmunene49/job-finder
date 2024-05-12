package com.example.jobfinder.profile.presentation

import User
import android.net.Uri


data class ProfileState(
    val isLoading: Boolean = false,
    val error: String = "",
    val profileData: User = User(),
    val userImageUri: Uri? = null,
    val userImage: ByteArray? = null
)
