package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

import android.content.IntentSender

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSignInSuccess: Boolean = false,
    val signInIntentSender: IntentSender? = null
)
