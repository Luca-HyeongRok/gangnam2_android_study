package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up

sealed class SignUpAction {
    object NavigateToMain : SignUpAction()
    object NavigateToLogin : SignUpAction()
}
