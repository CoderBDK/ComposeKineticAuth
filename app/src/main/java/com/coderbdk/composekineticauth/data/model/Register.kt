package com.coderbdk.composekineticauth.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Register(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    @Transient
    val confirmPassword: String,
    val verificationCode: String,
)
