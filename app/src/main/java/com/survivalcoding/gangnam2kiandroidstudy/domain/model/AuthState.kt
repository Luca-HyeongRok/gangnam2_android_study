package com.survivalcoding.gangnam2kiandroidstudy.domain.model

sealed interface AuthState {
    object Unknown : AuthState
    object Unauthenticated : AuthState
    object Authenticated : AuthState
}