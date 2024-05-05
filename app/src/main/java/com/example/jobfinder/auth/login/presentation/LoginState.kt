package com.example.jobfinder.auth.login.presentation

data class LoginState(
    val email: String = "",
    val emailErrorMessage: String? = "",
    val passwordErrorMessage: String? = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val showErroDialog: Boolean = false
) {
}