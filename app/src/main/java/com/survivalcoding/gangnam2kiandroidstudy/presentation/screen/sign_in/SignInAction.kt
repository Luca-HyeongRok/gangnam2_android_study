package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

sealed interface SignInAction {
    data class OnEmailChange(val email: String) : SignInAction
    data class OnPasswordChange(val password: String) : SignInAction
    object OnSignUpClick : SignInAction
    object OnSignInClick : SignInAction
    object OnGoogleClick : SignInAction
}