package com.example.jobfinder.auth.sign_up.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.auth.sign_up.domain.RegisterRepository
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
class SignUpViewModel @Inject constructor(
    private val repository: RegisterRepository,
    private val formValidator: FormValidatorRepository
):ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun event(event: SignUpEvents) {
        when (event) {
            is SignUpEvents.OnTypeFirstName -> {
                state = state.copy(firstName = event.firstName)
                validateFirstName()
            }

            is SignUpEvents.OnTypeEmail -> {
                state = state.copy(email = event.email)
                validateEmail()
            }

            is SignUpEvents.OnTypePassword -> {
                state = state.copy(password = event.password)
                validatePassword()
            }

            is SignUpEvents.OnTypeLastName -> {
                state = state.copy(lastName = event.lastName)
                validateLastName()
            }

            is SignUpEvents.OnClickRegister -> {
                val isEmailValid = validateEmail()
                val isPasswordValid = validatePassword()
                val isFirstNameValid = validateFirstName()
                val isLastNameValid = validateLastName()

                if (isEmailValid && isPasswordValid && isFirstNameValid &&
                    isLastNameValid
                ) {
                    registerUser(
                        state.email,
                        state.password,
                        state.firstName,
                        state.lastName,
                        state.isAdmin
                    )


                }
            }


            SignUpEvents.OnDismissErrorDialog -> {
                state = state.copy(
                    showErrorDialog = false
                )
            }

            SignUpEvents.OnClickAdmin -> {
                state = state.copy(
                    isAdmin = true
                )
                Timber.tag("RegisterViewModel").d("Switch to admin: ${state.isAdmin}")
            }

            SignUpEvents.OnClickUser -> {
                state = state.copy(
                    isAdmin = false
                )
                Timber.tag("RegisterViewModel").d("Switch to user: ${state.isAdmin}")
            }

        }
    }

    private fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        isAdmin: Boolean
    ) {
        viewModelScope.launch {
            repository.register(
                email,
                password,
                firstName,
                lastName,
                isAdmin).onEach {resource ->
                when (resource) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                        )
                        _uiEvent.send(UiEvent.OnSuccess("Registration successful"))
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            errorMessage = resource.message ?: "An unexpected error occurred",
                            showErrorDialog = true
                        )
                    }

                    is Resource.Loading -> {
                        Timber.tag("RegisterViewModel").d("Loading")
                        state = state.copy(
                            isLoading = true,
                        )
                    }

                    else -> {}
                }
            }.launchIn(this)
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

    private fun validateFirstName(): Boolean {
        val firstNameResult = formValidator.validateFirstName(state.firstName)
        state = state.copy(firstNameErrorMessage = firstNameResult.errorMessage)
        return firstNameResult.successful
    }

    private fun validateLastName(): Boolean {
        val lastNameResult = formValidator.validateLastName(state.lastName)
        state = state.copy(lastNameErrorMessage = lastNameResult.errorMessage)
        return lastNameResult.successful
    }
}