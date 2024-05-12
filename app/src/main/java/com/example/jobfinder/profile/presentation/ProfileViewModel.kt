package com.example.jobfinder.profile.presentation

import User
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.profile.domain.ProfileScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileScreenRepo
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getUserDetails()
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.OnClickImage -> {
                state = state.copy(
                    userImage = event.byteArray,
                    userImageUri = event.uri
                )
                if(state.userImage != null) {
                    updateProfileImage()
                }
            }

            ProfileEvents.OnClickLogOut -> {
                logout()
            }
        }
    }

    private fun updateProfileImage() {
        viewModelScope.launch {
            repository.updateProfileImage(state.userImage ?: byteArrayOf()).onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        )
                        _uiEvent.send(UiEvent.OnSuccess("Image Updated"))
                        Timber.tag("ProfileViewModel").d("Image Updated")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                        )
                        Timber.tag("ProfileViewModel").d("Error: ${it.message}")

                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                        Timber.tag("ProfileViewModel").d("Started upload")

                    }

                    else -> {}
                }
            }.launchIn(this)
        }
    }


    private fun getUserDetails() {
        viewModelScope.launch {
            repository.getProfileData().onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            profileData = it.data ?: User(),
                            isLoading = false
                        )
                        Timber.tag("ProfileViewModel").d("User: ${state.profileData}")
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }

                    else -> {}
                }
            }.launchIn(this)
        }
    }

    private fun logout() {
        viewModelScope.launch {
            repository.logout().onEach {
                when (it) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false
                        )
                        Timber.tag("HomeViewModel").d("logout Event")
                        _uiEvent.send(UiEvent.NavigateToLoginScreen)

                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = it.message ?: "An unexpected error occurred",
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }

                    else -> {}
                }
            }.launchIn(this)
        }
    }

}