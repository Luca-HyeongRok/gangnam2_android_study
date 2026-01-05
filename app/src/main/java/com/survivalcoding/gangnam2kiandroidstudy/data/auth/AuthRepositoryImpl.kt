package com.survivalcoding.gangnam2kiandroidstudy.data.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            Log.d("AuthRepository", "signIn start")
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()

            Log.d(
                "AuthRepository",
                "signIn success user=${firebaseAuth.currentUser?.uid}"
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("AuthRepository", "signIn failed", e)
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogle(
        idToken: String
    ): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserUid(): String? =
        firebaseAuth.currentUser?.uid

    override fun signOut() {
        firebaseAuth.signOut()
    }
}
