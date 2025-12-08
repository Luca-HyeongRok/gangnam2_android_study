package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun FilterButton(
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (isSelected) AppColors.primary100 else AppColors.white

    val contentColor =
        if (isSelected) AppColors.white else AppColors.primary80

    Row(
        modifier = modifier
            .width(60.dp)
            .height(28.dp)
            .background(backgroundColor, RoundedCornerShape(10.dp))
            .border(
                width = if (!isSelected) 1.dp else 0.dp,
                color = AppColors.primary80,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            style = AppTextStyles.smallTextRegular.copy(
                fontSize = 11.sp,
                color = contentColor
            ),
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterButtonPreview() {
    Row(
        modifier = Modifier.padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FilterButton(
            text = "Text",
            isSelected = false
        )
        FilterButton(
            text = "Text",
            isSelected = true
        )
    }
}
