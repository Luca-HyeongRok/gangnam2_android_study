package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state = _state.asStateFlow()

    private val _action = MutableSharedFlow<SignUpAction>()
    val action = _action.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        viewModelScope.launch {
            when (event) {
                is SignUpEvent.OnNameChanged -> _state.update { it.copy(name = event.name) }
                is SignUpEvent.OnEmailChanged -> _state.update { it.copy(email = event.email) }
                is SignUpEvent.OnPasswordChanged -> _state.update { it.copy(password = event.password) }
                is SignUpEvent.OnConfirmPasswordChanged -> _state.update { it.copy(confirmPassword = event.confirmPassword) }
                is SignUpEvent.OnAcceptTermsChanged -> _state.update { it.copy(acceptTerms = event.acceptTerms) }
                is SignUpEvent.OnSignUpClicked -> signUp()
            }
        }
    }

    private suspend fun signUp() {
        if (!isFormValid()) {
            _state.update { it.copy(error = "Please fill all fields and accept the terms.") }
            return
        }

        _state.update { it.copy(isLoading = true, error = null) }

        val result =
            authRepository.signUpWithEmail(state.value.email, state.value.password)

        if (result.isSuccess) {
            // Here you might want to also update the user's profile with the name
            // For simplicity, we are just signing up.
            _state.update { it.copy(isLoading = false, isSignUpSuccess = true) }
            _action.emit(SignUpAction.NavigateToMain)
        } else {
            _state.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message) }
        }
    }

    private fun isFormValid(): Boolean {
        val currentState = state.value
        val isPasswordMatch =
            currentState.password.isNotEmpty() && currentState.password == currentState.confirmPassword
        return currentState.name.isNotEmpty() &&
                currentState.email.isNotEmpty() &&
                isPasswordMatch &&
                currentState.acceptTerms
    }

    fun navigateToLogin() {
        viewModelScope.launch {
            _action.emit(SignUpAction.NavigateToLogin)
        }
    }
}
