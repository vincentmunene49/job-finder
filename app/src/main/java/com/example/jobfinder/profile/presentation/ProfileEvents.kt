package com.example.jobfinder.profile.presentation

sealed class ProfileEvents {

    object OnClickLogOut : ProfileEvents()

    object OnClickImage : ProfileEvents()
}