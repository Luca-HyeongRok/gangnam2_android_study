package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BigButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    BaseButton(
        text = text,
        size = ButtonSize.BIG,
        enabled = enabled,
        showArrow = true,
        modifier = modifier,
        onClick = onClick
    )
}

@Preview(showBackground = true)
@Composable
private fun BigButtonPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BigButton(
            text = "Button",
            onClick = {}
        )

        BigButton(
            text = "Button",
            enabled = false,
            onClick = {}
        )
    }
}
