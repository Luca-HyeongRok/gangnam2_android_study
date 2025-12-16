package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.survivalcoding.gangnam2kiandroidstudy.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors

@Composable
fun FilterBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.primary100)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_setting_4),
            contentDescription = "Filter",
            tint = AppColors.white,
            modifier = Modifier.size(20.dp)
        )
    }
}
