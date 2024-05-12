package com.example.jobfinder.auth.login.presentation

sealed class LoginEvents {
    data class OnTypeEmail(val email: String) : LoginEvents()

    data class OnTypePassword(val password: String) : LoginEvents()

    object  OnDismissErrorDialog : LoginEvents()

    object OnClickLogin : LoginEvents()

}