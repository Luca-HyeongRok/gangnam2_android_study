package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

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
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.SocialIconButtonsRow
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.InputField
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.BigButton
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun SignInScreen(
    state: SignInState,
    onAction: (SignInAction) -> Unit
) {
    // 이제 이 Composable은 어떠한 로직도, ViewModel도 알 필요가 없습니다.
    // 오직 전달받은 state로 UI를 그리고, onAction을 통해 이벤트를 전달할 뿐입니다.
    val snackbarHostState = remember { SnackbarHostState() }

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
                        onValueChange = { onAction(SignInAction.OnEmailChange(it)) },
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
                        onValueChange = { onAction(SignInAction.OnPasswordChange(it)) },
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Forgot Password?",
                        style = AppTextStyles.smallTextRegular.copy(color = AppColors.secondary100),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp)
                            .clickable { /* 'Forgot Password'는 아직 미구현 */ }
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    BigButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onAction(SignInAction.OnSignInClick) }
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
                        onGoogleClick = { onAction(SignInAction.OnGoogleClick) }
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
                        modifier = Modifier.clickable { onAction(SignInAction.OnSignUpClick) }
                    )
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
