package com.coderbdk.composekineticauth.ui.model

data class RegisterState(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val verificationCode: String,
)
