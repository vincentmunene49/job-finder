package com.example.jobfinder.main.common.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenSharedViewModel @Inject constructor():ViewModel() {

    var state by mutableStateOf(MainScreenSharedState())
        private set

    fun showBottomNavigation(show:Boolean){
        state = state.copy(showBottomNavigation = show)
    }
}