package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up

import androidx.compose.runtime.Composable

@Composable
fun SignUpRoot(
    onBackToSignIn: () -> Unit,
    onSignUpSuccess: () -> Unit,
) {
    SignUpScreen(
        onSignUpClick = {
            onSignUpSuccess()
        },
        onLoginClick = {
            onBackToSignIn()
        }
    )
}
