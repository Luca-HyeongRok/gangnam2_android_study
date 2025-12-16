package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

import androidx.compose.runtime.Composable

@Composable
fun SignInRoot(
    onSignUpClick: () -> Unit,
    onSignInSuccess: () -> Unit,
) {
    SignInScreen(
        onSignInClick = {
            onSignInSuccess()
        },
        onSignUpClick = onSignUpClick
    )
}
