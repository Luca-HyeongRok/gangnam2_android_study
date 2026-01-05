package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ButtonSize(
    val width: Dp,
    val height: Dp
) {
    BIG(315.dp, 60.dp),
    MEDIUM(243.dp, 54.dp),
    SMALL(174.dp, 37.dp)
}
