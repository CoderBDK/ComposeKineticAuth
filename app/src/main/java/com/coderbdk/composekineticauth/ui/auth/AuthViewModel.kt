package com.coderbdk.composekineticauth.ui.auth

import androidx.lifecycle.ViewModel
import com.coderbdk.composekineticauth.data.model.Login
import com.coderbdk.composekineticauth.data.model.Register
import com.coderbdk.composekineticauth.ui.model.AuthUiEvent
import com.coderbdk.composekineticauth.ui.model.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        AuthUiState(
            login = Login("", ""),
            register = Register(
                firstName = "",
                lastName = "",
                email = "",
                password = "",
                confirmPassword = "",
                verificationCode = ""
            )
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.LoginEvent.OnEmailChanged -> updateLoginEmail(event.email)
            is AuthUiEvent.LoginEvent.OnPasswordChanged -> updateLoginPassword(event.password)
            AuthUiEvent.LoginEvent.OnLoginRequest -> performLogin()
            is AuthUiEvent.RegisterEvent.OnFirstNameChanged -> updateRegisterFirstName(event.name)
            is AuthUiEvent.RegisterEvent.OnLastNameChanged -> updateRegisterLastName(event.name)
            is AuthUiEvent.RegisterEvent.OnEmailChanged -> updateRegisterEmail(event.email)
            is AuthUiEvent.RegisterEvent.OnPasswordChanged -> updateRegisterPassword(event.password)
            is AuthUiEvent.RegisterEvent.OnConfirmPasswordChanged -> updateRegisterConfirmPassword(event.password)
            is AuthUiEvent.RegisterEvent.OnVerificationCodeChanged -> updateVerificationCode(event.code)
            AuthUiEvent.RegisterEvent.OnSendCode -> sendVerificationCode()
            AuthUiEvent.RegisterEvent.OnRegisterRequest -> performRegister()
        }
    }

    private fun updateLoginEmail(email: String) {
        _uiState.update { it.copy(login = it.login.copy(email = email)) }
    }

    private fun updateLoginPassword(password: String) {
        _uiState.update { it.copy(login = it.login.copy(password = password)) }
    }

    private fun performLogin() {

    }

    private fun updateRegisterFirstName(name: String) {
        _uiState.update { it.copy(register = it.register.copy(firstName = name)) }
    }

    private fun updateRegisterLastName(name: String) {
        _uiState.update { it.copy(register = it.register.copy(lastName = name)) }
    }

    private fun updateRegisterEmail(email: String) {
        _uiState.update { it.copy(register = it.register.copy(email = email)) }
    }

    private fun updateRegisterPassword(password: String) {
        _uiState.update { it.copy(register = it.register.copy(password = password)) }
    }

    private fun updateRegisterConfirmPassword(password: String) {
        _uiState.update { it.copy(register = it.register.copy(confirmPassword = password)) }
    }

    private fun updateVerificationCode(code: String) {
        _uiState.update { it.copy(register = it.register.copy(verificationCode = code)) }
    }
    private fun sendVerificationCode() {

    }

    private fun performRegister() {

    }
}
