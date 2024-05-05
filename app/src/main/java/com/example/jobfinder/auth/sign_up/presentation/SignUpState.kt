package com.example.jobfinder.auth.sign_up.presentation

data class SignUpState(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val emailErrorMessage: String? = "",
    val firstNameErrorMessage: String? = "",
    val lastNameErrorMessage: String? = "",
    val passwordErrorMessage: String? = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showErrorDialog: Boolean = false,
    val imagePath:String = "",
    val adminClicked:Boolean = false,
    val userClicked:Boolean = false,
    val isAdmin:Boolean = true,
    val isUser:Boolean = false

)
