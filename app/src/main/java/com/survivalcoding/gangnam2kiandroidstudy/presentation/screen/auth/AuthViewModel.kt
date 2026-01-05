package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.AuthRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _userLoggedIn = MutableStateFlow(false)
    val userLoggedIn: StateFlow<Boolean> = _userLoggedIn

    fun signInWithGoogle() {
        viewModelScope.launch {
            val result = runCatching {
                val idToken = googleAuthUiClient.getIdToken()
                authRepository.signInWithGoogle(idToken)
            }.getOrElse {
                _userLoggedIn.value = false
                return@launch
            }

            _userLoggedIn.value = result.isSuccess
        }
    }

    fun signOut() {
        authRepository.signOut()
        _userLoggedIn.value = false
    }
}
