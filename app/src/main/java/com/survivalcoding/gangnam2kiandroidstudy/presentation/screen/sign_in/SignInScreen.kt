package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.auth.GoogleAuthUiClient
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.SocialIconButtonsRow
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.InputField
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.BigButton
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@Composable
fun SignInScreen(
    onSignInSuccess: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    val viewModel: SignInViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Koin을 통해 GoogleAuthUiClient 주입
    val googleAuthUiClient: GoogleAuthUiClient = koinInject()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    viewModel.onEvent(SignInEvent.OnSignInResult(it))
                }
            } else {
                // 사용자가 로그인 창을 닫는 등의 케이스 처리
                viewModel.onEvent(SignInEvent.OnSignInLaunched) // 로딩 중 상태 해제
            }
        }
    )

    LaunchedEffect(state.shouldLaunchSignIn) {
        if (state.shouldLaunchSignIn) {
            launcher.launch(googleAuthUiClient.getSignInIntent())
            viewModel.onEvent(SignInEvent.OnSignInLaunched)
        }
    }

    LaunchedEffect(viewModel.action) {
        viewModel.action.collectLatest { action ->
            when (action) {
                is SignInAction.NavigateToMain -> onSignInSuccess()
                is SignInAction.NavigateToSignUp -> onSignUpClick()
                is SignInAction.NavigateToForgotPassword -> onForgotPasswordClick()
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
                    modifier = Modifier.padding(start = 30.dp, top = 94.dp)
                ) {
                    Text(
                        text = "Hello,",
                        style = AppTextStyles.headerTextBold,
                        color = AppColors.black,
                    )
                    Text(
                        text = "Welcome Back!",
                        style = AppTextStyles.largeTextRegular,
                        color = AppColors.gray2,
                    )
                }

                Spacer(modifier = Modifier.height(57.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    InputField(
                        label = "Email",
                        text = state.email,
                        placeholder = "Enter email",
                        onValueChange = { viewModel.onEvent(SignInEvent.OnEmailChanged(it)) },
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    InputField(
                        label = "Enter Password",
                        text = state.password,
                        placeholder = "Enter password",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                        ),
                        visualTransformation = PasswordVisualTransformation(),
                        onValueChange = { viewModel.onEvent(SignInEvent.OnPasswordChanged(it)) },
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Forgot Password?",
                        style = AppTextStyles.smallTextRegular.copy(color = AppColors.secondary100),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp)
                            .clickable { viewModel.navigateToForgotPassword() }
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    BigButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.onEvent(SignInEvent.OnSignInClicked) }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 60.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.width(50.dp),
                            color = AppColors.gray4
                        )
                        Text(
                            text = "Or Sign in With",
                            modifier = Modifier.padding(horizontal = 7.dp),
                            style = AppTextStyles.smallerTextBold.copy(color = AppColors.gray4)
                        )
                        HorizontalDivider(
                            modifier = Modifier.width(50.dp),
                            color = AppColors.gray4
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    SocialIconButtonsRow(
                        onGoogleClick = { viewModel.onEvent(SignInEvent.OnGoogleSignInClicked) }
                    )

                    Spacer(modifier = Modifier.height(55.dp))

                    Text(
                        text = buildAnnotatedString {
                            append("Don't have an account? ")
                            withStyle(
                                style = SpanStyle(color = AppColors.secondary100)
                            ) {
                                append("Sign up")
                            }
                        },
                        style = AppTextStyles.smallerTextRegular,
                        modifier = Modifier.clickable { viewModel.navigateToSignUp() }
                    )
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
private fun SignInScreenPreview() {
    SignInScreen()
}
