package com.coderbdk.composekineticauth.ui.model

sealed class AuthUiEvent {

    sealed class LoginEvent : AuthUiEvent() {
        data class OnEmailChanged(val email: String) : LoginEvent()
        data class OnPasswordChanged(val password: String) : LoginEvent()
        data object OnLoginRequest : LoginEvent()
    }

    sealed class RegisterEvent : AuthUiEvent() {
        data class OnFirstNameChanged(val name: String) : RegisterEvent()
        data class OnLastNameChanged(val name: String) : RegisterEvent()
        data class OnEmailChanged(val email: String) : RegisterEvent()
        data class OnPasswordChanged(val password: String) : RegisterEvent()
        data class OnConfirmPasswordChanged(val password: String) : RegisterEvent()
        data class OnVerificationCodeChanged(val code: String) : RegisterEvent()
        data object OnSendCode: RegisterEvent()
        data object OnRegisterRequest : RegisterEvent()
    }
}