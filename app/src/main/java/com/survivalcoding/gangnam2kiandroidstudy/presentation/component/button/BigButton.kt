package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun BigButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    // Pressed 상태 감지 (마우스를 누르고 있는 동안 true)
    val isPressed by interactionSource.collectIsPressedAsState()
    // Pressed 이거나 Disabled 인 경우 동일한 스타일 적용
    val isDisabledStyle = !enabled || isPressed

    // 상태별 색상 지정
    val backgroundColor =
        if (isDisabledStyle) AppColors.gray4 else AppColors.primary100
    val contentColor =
        if (isDisabledStyle) AppColors.white.copy(alpha = 0.6f) else AppColors.white

    Box(
        modifier = modifier
            .width(315.dp)
            .height(60.dp)
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .clickable(
                enabled = enabled, // Disabled 상태 클릭 무효화
                interactionSource = interactionSource,
                indication = null // Ripple 효과 제거
            ) {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                style = AppTextStyles.normalTextBold.copy(color = contentColor)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Arrow Icon",
                tint = contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BigButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BigButton(text = "Button", enabled = true)
        BigButton(text = "Button", enabled = false)
    }
}
