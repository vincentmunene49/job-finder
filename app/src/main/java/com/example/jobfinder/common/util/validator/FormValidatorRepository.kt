package com.example.jobfinder.common.util.validator

interface FormValidatorRepository {
    fun validateEmail(email: String): ValidationResult
    fun validatePassword(password: String): ValidationResult
    fun validateFirstName(username: String): ValidationResult
    fun validateLastName(username: String): ValidationResult

}