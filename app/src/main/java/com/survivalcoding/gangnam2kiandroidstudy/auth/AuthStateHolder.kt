package com.survivalcoding.gangnam2kiandroidstudy.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthStateHolder {

    private lateinit var firebaseAuth: FirebaseAuth

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unknown)
    val authState = _authState.asStateFlow()

    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        val user = auth.currentUser
        Log.d("AuthStateHolder", "currentUser = $user")

        _authState.value = if (auth.currentUser != null) {
            AuthState.Authenticated
        } else {
            AuthState.Unauthenticated
        }
    }

    fun init(firebaseAuth: FirebaseAuth) {
        this.firebaseAuth = firebaseAuth
    }

    fun startListening() {
        check(::firebaseAuth.isInitialized) {
            "AuthStateHolder.init(firebaseAuth) must be called before startListening()"
        }

        _authState.value = if (firebaseAuth.currentUser != null) {
            AuthState.Authenticated
        } else {
            AuthState.Unauthenticated
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

    fun stopListening() {
        if (::firebaseAuth.isInitialized) {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }
}
