package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

interface AuthRepository {
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<Unit>
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit>
    fun getCurrentUserUid(): String?
    fun signOut()
    // Add other authentication methods as needed
}
