package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SmallButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    BaseButton(
        text = text,
        size = ButtonSize.SMALL,
        enabled = enabled,
        showArrow = false,
        modifier = modifier,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun SmallButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallButton(text = "Button", onClick = {})
        SmallButton(text = "Button", enabled = false, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun AllButtonsPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BigButton("Big", onClick = {})
        BigButton("Big", enabled = false, onClick = {})

        MediumButton("Medium", onClick = {})
        MediumButton("Medium", enabled = false, onClick = {})

        SmallButton("Small", onClick = {})
        SmallButton("Small", enabled = false, onClick = {})
    }
}
