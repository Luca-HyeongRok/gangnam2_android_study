package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

sealed interface SignInAction {
    data object NavigateToMain : SignInAction
    data object NavigateToSignUp : SignInAction
    data object NavigateToForgotPassword : SignInAction
}