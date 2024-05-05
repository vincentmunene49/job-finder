package com.example.jobfinder.common.util.validator

import android.util.Patterns

class DefaultFormValidatorRepositoryImpl : FormValidatorRepository{

    override fun validateEmail(email: String): ValidationResult {
        if(email.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The email can not be blank"
            )

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }

    override fun validatePassword(password: String): ValidationResult {
        if(password.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The password can not be blank"
            )
        }
        if(password.length < 8){
            return ValidationResult(
                successful = false,
                errorMessage = "The password must be at least 8 characters long"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }

    override fun validateFirstName(username: String): ValidationResult {
        if(username.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The first name can not be blank"
            )
        }
        if(username.length< 2){
            return ValidationResult(
                successful = false,
                errorMessage = "The first name must be at least 2 characters long"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }

    override fun validateLastName(username: String): ValidationResult {
        if(username.isBlank()){
            return ValidationResult(
                successful = false,
                errorMessage = "The last name can not be blank"
            )
        }
        if(username.length< 2){
            return ValidationResult(
                successful = false,
                errorMessage = "The last name must be at least 2 characters long"
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }


}