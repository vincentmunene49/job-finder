package com.example.jobfinder.auth.sign_up.presentation

sealed class SignUpEvents {
    data class OnTypeFirstName(val firstName: String) : SignUpEvents()
    data class OnTypeLastName(val lastName: String) : SignUpEvents()
    data class OnTypeEmail(val email: String) : SignUpEvents()
    data class OnTypePassword(val password: String) : SignUpEvents()
    object OnClickRegister : SignUpEvents()

    object OnDismissErrorDialog : SignUpEvents()


    object OnClickAdmin : SignUpEvents()

    object OnClickUser : SignUpEvents()
}