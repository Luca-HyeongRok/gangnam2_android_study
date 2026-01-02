package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up

sealed class SignUpEvent {
    data class OnNameChanged(val name: String) : SignUpEvent()
    data class OnEmailChanged(val email: String) : SignUpEvent()
    data class OnPasswordChanged(val password: String) : SignUpEvent()
    data class OnConfirmPasswordChanged(val confirmPassword: String) : SignUpEvent()
    data class OnAcceptTermsChanged(val acceptTerms: Boolean) : SignUpEvent()
    object OnSignUpClicked : SignUpEvent()
}
