package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

interface AuthRepository {

    /**
     * 이메일 / 비밀번호 로그인
     */
    suspend fun signInWithEmail(email: String, password: String): Result<Unit>

    /**
     * Google 로그인 (CredentialManager → idToken)
     */
    suspend fun signInWithGoogle(idToken: String): Result<Unit>

    /**
     * 이메일 / 비밀번호 회원가입 (추후 SignUp 화면용)
     */
    suspend fun signUpWithEmail(email: String, password: String): Result<Unit>

    /**
     * 현재 로그인된 유저 UID
     */
    fun getCurrentUserUid(): String?

    /**
     * 로그아웃
     */
    fun signOut()
}
