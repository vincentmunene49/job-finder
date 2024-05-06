package com.example.jobfinder.user.main.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeNavigationSharedViewModel @Inject constructor() :ViewModel() {

    var state by mutableStateOf(HomeNavigationSharedStates())
        private set

}