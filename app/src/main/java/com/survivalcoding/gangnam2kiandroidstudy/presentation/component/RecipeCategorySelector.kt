package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors

@Composable
fun RecipeCategorySelector(
    modifier: Modifier = Modifier,
    categories: List<String>,
    selectedCategory: String,
    onSelectCategory: (String) -> Unit = {},
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(AppColors.white)
            .padding(vertical = 10.dp)
    ) {
        item {
            Spacer(modifier = Modifier.width(30.dp))
        }
        items(categories) {
            RecipeCategoryItem(
                text = it,
                isSelected = it == selectedCategory,
                onClick = {
                    onSelectCategory(it)
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun RecipeCategorySelectorPreview() {
    RecipeCategorySelector(
        categories = listOf("Chinese", "Korean", "Japanese", "Western"),
        selectedCategory = "Chinese"
    )
}