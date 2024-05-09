package com.example.jobfinder.user.home.presentation

sealed class HomeEvent {

    data class OnClickJobItem(val jobId:String):HomeEvent()
    object OnClickClear : HomeEvent()

    data class OnTypeSearchValue(val search:String):HomeEvent()
}