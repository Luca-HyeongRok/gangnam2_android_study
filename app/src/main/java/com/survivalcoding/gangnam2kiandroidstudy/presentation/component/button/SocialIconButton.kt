package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.R

@Composable
fun SocialIconButton(
    iconResId: Int,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(AppColors.white, CircleShape)
            .border(BorderStroke(1.dp, AppColors.gray4), CircleShape)
            .clickable{ onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SocialIconButtonsRow(
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        SocialIconButton(
            iconResId = R.drawable.social_icons_google,
            onClick = onGoogleClick
        )
        Spacer(modifier = Modifier.width(25.dp))

        SocialIconButton(
            iconResId = R.drawable.social_icons_facebook,
            onClick = onFacebookClick
        )
    }
}