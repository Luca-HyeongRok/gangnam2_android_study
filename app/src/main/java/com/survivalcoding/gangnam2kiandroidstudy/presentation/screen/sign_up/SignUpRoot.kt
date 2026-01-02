package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up

import androidx.compose.runtime.Composable

@Composable
fun SignUpRoot(
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit,
) {
    SignUpScreen(
        onSignUpSuccess = onSignUpSuccess,
        onLoginClick = onLoginClick
    )
}