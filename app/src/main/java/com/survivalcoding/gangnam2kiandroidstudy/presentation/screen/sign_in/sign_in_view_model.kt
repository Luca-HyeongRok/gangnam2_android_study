package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.AuthRepository
import com.survivalcoding.gangnam2kiandroidstudy.presentation.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _action = MutableSharedFlow<SignInAction>()
    val action = _action.asSharedFlow()

    fun onEvent(event: SignInEvent) {
        viewModelScope.launch {
            when (event) {
                is SignInEvent.OnEmailChanged -> {
                    _state.update { it.copy(email = event.email) }
                }

                is SignInEvent.OnPasswordChanged -> {
                    _state.update { it.copy(password = event.password) }
                }

                is SignInEvent.OnSignInClicked -> {
                    signInWithEmailAndPassword()
                }

                is SignInEvent.OnGoogleSignInClicked -> {
                    signInWithGoogle()
                }
            }
        }
    }

    private suspend fun signInWithEmailAndPassword() {
        _state.update { it.copy(isLoading = true, error = null) }
        val result = authRepository.signInWithEmailAndPassword(state.value.email, state.value.password)
        if (result.isSuccess) {
            _state.update { it.copy(isLoading = false, isSignInSuccess = true) }
            _action.emit(SignInAction.NavigateToMain)
        } else {
            _state.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
        }
    }

    private suspend fun signInWithGoogle() {
        _state.update { it.copy(isLoading = true, error = null) }
        val signInIntentSender = googleAuthUiClient.signIn()
        _state.update { it.copy(signInIntentSender = signInIntentSender) }
    }

    fun onSignInResult(intent: Intent) {
        viewModelScope.launch {
            val result = googleAuthUiClient.signInWithIntent(intent)
            if (result.data != null) {
                _state.update { it.copy(isLoading = false, isSignInSuccess = true) }
                _action.emit(SignInAction.NavigateToMain)
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = result.errorMessage
                    )
                }
            }
        }
    }
    
    fun resetSignInIntentSender() {
        _state.update { it.copy(signInIntentSender = null) }
    }

    fun navigateToSignUp() {
        viewModelScope.launch {
            _action.emit(SignInAction.NavigateToSignUp)
        }
    }

    fun navigateToForgotPassword() {
        viewModelScope.launch {
            _action.emit(SignInAction.NavigateToForgotPassword)
        }
    }
}
