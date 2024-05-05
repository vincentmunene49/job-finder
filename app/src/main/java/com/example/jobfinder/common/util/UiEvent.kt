package com.example.jobfinder.common.util

sealed class UiEvent {
    data class OnSuccess(val message: String) : UiEvent()
    data class NavigateToAdminScreens(val message: String) : UiEvent()

    data class NavigateToUserScreens(val message: String) : UiEvent()
    object NavigateToLoginScreen : UiEvent()

    object Logout : UiEvent()


}