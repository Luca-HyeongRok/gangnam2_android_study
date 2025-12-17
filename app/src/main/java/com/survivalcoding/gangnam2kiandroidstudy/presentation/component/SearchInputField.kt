package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles
import com.survivalcoding.gangnam2kiandroidstudy.R
import androidx.compose.runtime.*

@Composable
fun SearchInputField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            readOnly = readOnly,
            enabled = (onClick == null),
            textStyle = AppTextStyles.smallerTextRegular.copy(
                fontSize = 11.sp,
                lineHeight = 17.sp,
            ),
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(
                    color = AppColors.white,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    width = 1.3.dp,
                    color = if (isFocused) AppColors.primary80 else AppColors.gray4,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 10.dp)
                .onFocusChanged() {
                    isFocused = it.isFocused
                },
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(18.dp),
                            painter = painterResource(id = R.drawable.outline_search_normal),
                            contentDescription = null,
                            tint = AppColors.gray4,
                        )
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (value.isBlank()) {
                                Text(
                                    text = placeholder,
                                    style = AppTextStyles.smallerTextRegular.copy(
                                        fontSize = 11.sp,
                                        lineHeight = 17.sp,
                                        color = AppColors.gray4
                                    ),
                                )
                            }
                            innerTextField()
                        }
                    }
                }
            }
        )
        if (onClick != null) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { onClick() }
            )
        }
    }
}
