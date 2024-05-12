package com.example.jobfinder.profile.presentation

import User


data class ProfileState(
    val isLoading: Boolean = false,
    val error: String = "",
    val profileData: User = User()
)
