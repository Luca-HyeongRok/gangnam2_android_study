package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

sealed interface SignInEvent {
    object NavigateToHome : SignInEvent
    object NavigateToSignUp : SignInEvent
    object LaunchGoogleSignIn : SignInEvent
    data class ShowError(val message: String) : SignInEvent
}