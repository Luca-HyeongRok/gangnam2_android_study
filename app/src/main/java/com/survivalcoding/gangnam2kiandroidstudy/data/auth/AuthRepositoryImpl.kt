package com.survivalcoding.gangnam2kiandroidstudy.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<Unit> {
        return runCatching {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Unit
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit> {
        return runCatching {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Unit
        }
    }

    override fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}
