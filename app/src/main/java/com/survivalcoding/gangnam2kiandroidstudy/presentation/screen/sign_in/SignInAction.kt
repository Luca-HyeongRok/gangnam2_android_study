package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

sealed class SignInAction {
    object NavigateToMain : SignInAction()
    object NavigateToSignUp : SignInAction()
    object NavigateToForgotPassword : SignInAction()
}
