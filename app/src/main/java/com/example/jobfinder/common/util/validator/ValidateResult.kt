package com.example.jobfinder.common.util.validator

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)