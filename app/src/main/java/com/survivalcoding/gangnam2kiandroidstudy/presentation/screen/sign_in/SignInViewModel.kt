package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.presentation.auth.GoogleAuthUiClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    private val googleAuthUiClient: GoogleAuthUiClient
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _action = Channel<SignInAction>()
    val action = _action.receiveAsFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChanged -> _state.update { it.copy(email = event.email) }
            is SignInEvent.OnPasswordChanged -> _state.update { it.copy(password = event.password) }
            SignInEvent.OnSignInClicked -> {
                // 이메일/비밀번호 로그인 로직 (현재는 비워둠)
            }
            SignInEvent.OnGoogleSignInClicked -> {
                _state.update { it.copy(shouldLaunchSignIn = true, isLoading = true) }
            }
            is SignInEvent.OnSignInResult -> {
                viewModelScope.launch {
                    val result = googleAuthUiClient.signInWithIntent(event.intent)
                    _state.update { it.copy(isLoading = false) }

                    if (result.data != null) {
                        _action.send(SignInAction.NavigateToMain)
                    } else {
                        _state.update { it.copy(error = result.errorMessage) }
                    }
                }
            }
            SignInEvent.OnSignInLaunched -> {
                _state.update { it.copy(shouldLaunchSignIn = false) }
            }
        }
    }

    fun navigateToSignUp() {
        viewModelScope.launch {
            _action.send(SignInAction.NavigateToSignUp)
        }
    }

    fun navigateToForgotPassword() {
        viewModelScope.launch {
            _action.send(SignInAction.NavigateToForgotPassword)
        }
    }
}
