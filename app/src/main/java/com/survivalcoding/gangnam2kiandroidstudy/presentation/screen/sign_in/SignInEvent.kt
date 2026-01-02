package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

sealed class SignInEvent {
    data class OnEmailChanged(val email: String) : SignInEvent()
    data class OnPasswordChanged(val password: String) : SignInEvent()
    object OnSignInClicked : SignInEvent()
    object OnGoogleSignInClicked : SignInEvent()
}
