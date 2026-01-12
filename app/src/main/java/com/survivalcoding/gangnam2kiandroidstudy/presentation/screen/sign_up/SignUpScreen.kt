package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.BigButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.SocialIconButtonsRow
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.InputField
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {},
) {
    val viewModel: SignUpViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    val isPasswordMatch =
        state.password.isNotEmpty() && state.password == state.confirmPassword

    val isFormValid =
        state.name.isNotEmpty() &&
                state.email.isNotEmpty() &&
                isPasswordMatch &&
                state.acceptTerms

    /**
     * 이벤트 Flow는 Unit 키로 한 번만 수집
     */
    LaunchedEffect(Unit) {
        viewModel.action.collectLatest { action ->
            when (action) {
                is SignUpAction.NavigateToMain -> onSignUpSuccess()
                is SignUpAction.NavigateToLogin -> onLoginClick()
            }
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(start = 30.dp, top = 54.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = "Create an account",
                        style = AppTextStyles.largeTextBold,
                        color = AppColors.black
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        text = "Let’s help you set up your account.",
                        style = AppTextStyles.smallerTextRegular,
                        color = AppColors.gray2
                    )
                    Text(
                        text = "It won’t take long.",
                        style = AppTextStyles.smallerTextRegular,
                        color = AppColors.gray2
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    InputField(
                        label = "Name",
                        placeholder = "Enter Name",
                        text = state.name,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    ) { viewModel.onEvent(SignUpEvent.OnNameChanged(it)) }

                    Spacer(modifier = Modifier.height(20.dp))

                    InputField(
                        label = "Email",
                        placeholder = "Enter Email",
                        text = state.email,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    ) { viewModel.onEvent(SignUpEvent.OnEmailChanged(it)) }

                    Spacer(modifier = Modifier.height(20.dp))

                    InputField(
                        label = "Password",
                        placeholder = "Enter Password",
                        text = state.password,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                    ) { viewModel.onEvent(SignUpEvent.OnPasswordChanged(it)) }

                    Spacer(modifier = Modifier.height(20.dp))

                    InputField(
                        label = "Confirm Password",
                        placeholder = "Re-enter Password",
                        text = state.confirmPassword,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                    ) { viewModel.onEvent(SignUpEvent.OnConfirmPasswordChanged(it)) }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = state.acceptTerms,
                                onValueChange = {
                                    viewModel.onEvent(SignUpEvent.OnAcceptTermsChanged(it))
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = state.acceptTerms,
                            onCheckedChange = {
                                viewModel.onEvent(SignUpEvent.OnAcceptTermsChanged(it))
                            }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Accept terms & conditions",
                            style = AppTextStyles.smallTextRegular.copy(
                                color = AppColors.secondary100
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    BigButton(
                        text = "Sign Up",
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isFormValid,
                        onClick = {
                            viewModel.onEvent(SignUpEvent.OnSignUpClicked)
                        }
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.padding(horizontal = 60.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.width(50.dp),
                            color = AppColors.gray4
                        )
                        Text(
                            text = "Or Sign up With",
                            modifier = Modifier.padding(horizontal = 7.dp),
                            style = AppTextStyles.smallerTextBold.copy(color = AppColors.gray4)
                        )
                        HorizontalDivider(
                            modifier = Modifier.width(50.dp),
                            color = AppColors.gray4
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    SocialIconButtonsRow()

                    Spacer(modifier = Modifier.height(19.dp))

                    Text(
                        text = buildAnnotatedString {
                            append("Already a member? ")
                            withStyle(style = SpanStyle(color = AppColors.secondary100)) {
                                append("Log in")
                            }
                        },
                        style = AppTextStyles.smallerTextBold,
                        modifier = Modifier.clickable {
                            viewModel.navigateToLogin()
                        }
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen()
}
