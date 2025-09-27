package com.coderbdk.composekineticauth.ui.model

import com.coderbdk.composekineticauth.data.model.Login
import com.coderbdk.composekineticauth.data.model.Register

data class AuthUiState(
    val login: Login,
    val register: Register
)