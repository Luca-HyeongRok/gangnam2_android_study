package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles
import androidx.compose.runtime.getValue

@Composable
fun SmallButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isDisabledStyle = !enabled || isPressed

    val backgroundColor =
        if (isDisabledStyle) AppColors.gray4 else AppColors.primary100
    val contentColor =
        if (isDisabledStyle) AppColors.white.copy(alpha = 0.6f) else AppColors.white

    Box(
        modifier = modifier
            .width(174.dp)
            .height(37.dp)
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                style = AppTextStyles.normalTextBold.copy(color = contentColor)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SmallButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallButton(text = "Button", enabled = true)
        SmallButton(text = "Button", enabled = false)
    }
}

@Preview(showBackground = true)
@Composable
private fun AllButtonsPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BigButton(text = "Button", enabled = true)
        BigButton(text = "Button", enabled = false)

        MediumButton(text = "Button", enabled = true)
        MediumButton(text = "Button", enabled = false)

        SmallButton(text = "Button", enabled = true)
        SmallButton(text = "Button", enabled = false)
    }
}
