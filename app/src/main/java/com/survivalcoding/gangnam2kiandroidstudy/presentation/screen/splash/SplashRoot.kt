package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.splash

import androidx.compose.runtime.Composable
import com.survivalcoding.gangnam2kiandroidstudy.presentation.splash.SplashScreen

@Composable
fun SplashRoot(
    onStartClick: () -> Unit
) {
    SplashScreen(
        onStartClick = onStartClick
    )
}
