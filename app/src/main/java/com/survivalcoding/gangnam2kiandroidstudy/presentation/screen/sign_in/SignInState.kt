package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
