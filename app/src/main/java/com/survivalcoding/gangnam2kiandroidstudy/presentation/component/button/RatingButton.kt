package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun RatingButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (isSelected) AppColors.primary100 else AppColors.white

    val contentColor =
        if (isSelected) AppColors.white else AppColors.primary80

    val borderColor =
        if (!isSelected) AppColors.primary80 else backgroundColor

    Box(
        modifier = modifier
            .width(50.dp)
            .height(28.dp)
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .border(
                width = if (!isSelected) 1.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = text,
                style = AppTextStyles.smallTextRegular.copy(
                    fontSize = 11.sp,
                    color = contentColor
                )
            )
            Spacer(modifier = Modifier.size(5.dp))
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "rating",
                tint = contentColor,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RatingButtonPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        RatingButton(text = "5", isSelected = true, onClick = {})
        RatingButton(text = "4", isSelected = false, onClick = {})
    }
}
