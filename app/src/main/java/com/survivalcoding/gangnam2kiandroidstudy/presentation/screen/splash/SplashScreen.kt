package com.survivalcoding.gangnam2kiandroidstudy.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.MediumButton
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles
import androidx.compose.ui.graphics.Brush

@Composable
fun SplashScreen(
    onStartClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        // 배경
        Image(
            painter = painterResource(R.drawable.splash_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 어둡게
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            AppColors.black.copy(alpha = 0.55f),
                            AppColors.black.copy(alpha = 0.85f),
                        )
                    )
                )
        )


        // 상단 로고 + 문구
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(104.dp))

            Image(
                painter = painterResource(R.drawable.splash_logo),
                contentDescription = "Splash Logo",
                modifier = Modifier.size(74.dp) // 친구는 74
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "100K+ Premium Recipe",
                style = AppTextStyles.mediumTextBold,
                color = AppColors.white
            )
        }

        // 중앙 텍스트
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 202.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Get\nCooking",
                style = AppTextStyles.titleTextBold,
                color = AppColors.white,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Simple way to find Tasty Recipe",
                style = AppTextStyles.normalTextRegular,
                color = AppColors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 50.dp)
            )
        }


        // 하단 버튼
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 84.dp)
        ) {
            MediumButton(
                text = "Start Cooking",
                modifier = Modifier.padding(horizontal = 66.dp),
                onClick = onStartClick
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun SplashScreenPreview() {
//    SplashScreen()
//}
