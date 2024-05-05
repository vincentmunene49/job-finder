package com.example.jobfinder.auth.login.presentation

import Resource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.auth.login.domain.LoginRepository
import com.example.jobfinder.common.util.UiEvent
import com.example.jobfinder.common.util.validator.FormValidatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val formValidator: FormValidatorRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun event(event: LoginEvents) {
        when (event) {
            is LoginEvents.OnTypeEmail -> {
                state = state.copy(email = event.email)
                validateEmail()
            }

            is LoginEvents.OnTypePassword -> {
                state = state.copy(password = event.password)
                validatePassword()
            }

            is LoginEvents.OnClickLogin -> {
                val isEmailValid = validateEmail()
                val isPasswordValid = validatePassword()
                if (isEmailValid && isPasswordValid) {
                    login(
                        state.email,
                        state.password
                    )
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {

            repository.login(email, password).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                        )
                        val data = resource.data
                        if (data != null) {
                            val (user, userType) = data
                            if (userType == "admin") {
                                _uiEvent.send(UiEvent.NavigateToAdminScreens("Admin"))

                            } else {
                                _uiEvent.send(UiEvent.NavigateToUserScreens("User"))

                            }
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "An unexpected error occurred",
                            showErroDialog = true
                        )
                        Timber.tag("LoginViewModel").d("Error: ${resource.message}")
                    }

                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true,
                        )
                    }

                    else -> {}
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun validateEmail(): Boolean {
        val emailResult = formValidator.validateEmail(state.email)
        state = state.copy(emailErrorMessage = emailResult.errorMessage)
        return emailResult.successful
    }

    private fun validatePassword(): Boolean {
        val passwordResult = formValidator.validatePassword(state.password)
        state = state.copy(passwordErrorMessage = passwordResult.errorMessage)
        return passwordResult.successful
    }
}