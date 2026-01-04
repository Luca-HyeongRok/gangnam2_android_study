package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

import android.content.Intent

sealed interface SignInEvent {
    data class OnEmailChanged(val email: String) : SignInEvent
    data class OnPasswordChanged(val password: String) : SignInEvent
    data object OnSignInClicked : SignInEvent
    data object OnGoogleSignInClicked : SignInEvent
    data class OnSignInResult(val intent: Intent) : SignInEvent
    data object OnSignInLaunched : SignInEvent
}