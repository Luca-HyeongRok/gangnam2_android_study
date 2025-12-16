package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
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
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.SocialIconButtonsRow
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.InputField
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.BigButton
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles


@Composable
fun SignInScreen(
    onSignInClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                text = email,
                placeholder = "Enter email",
                onValueChange = { email = it },
            )
            Spacer(modifier = Modifier.height(30.dp))

            InputField(
                label = "Enter Password",
                text = password,
                placeholder = "Enter password",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                ),
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { password = it },
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Forgot Password?",
                style = AppTextStyles.smallTextRegular.copy(color = AppColors.secondary100),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 10.dp)
                    .clickable { onForgotPasswordClick() }
            )

            Spacer(modifier = Modifier.height(25.dp))

            BigButton(
                text = "Sign In",
                modifier = Modifier.fillMaxWidth(),
                onClick = onSignInClick
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

            SocialIconButtonsRow()

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
                modifier = Modifier.clickable { onSignUpClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    SignInScreen()
}
