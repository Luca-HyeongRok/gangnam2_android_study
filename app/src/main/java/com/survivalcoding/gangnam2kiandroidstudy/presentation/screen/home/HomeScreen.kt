package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home

import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.HomeRecipeCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.FilterBox
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCategorySelector
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SearchInputField
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun HomeScreen(
    state: HomeState = HomeState(),
    onSelectCategory: (String) -> Unit = {},
    profilePainter: Painter,
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.white),
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Hello Jega",
                    style = AppTextStyles.largeTextBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "What are you cooking today?",
                    style = AppTextStyles.smallerTextRegular.copy(color = AppColors.gray3)
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(color = AppColors.secondary40, shape = RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = profilePainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchInputField(
                modifier = Modifier.weight(1f),
                value = "",
                placeholder = "Search Recipe",
            ) {
            }
            Spacer(modifier = Modifier.width(20.dp))
            FilterBox {
                onFilterClick()
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        RecipeCategorySelector(
            categories = listOf(
                "All", "Indian", "Italian", "Asian", "Chinese",
                "Fruit", "Vegetables", "Protein", "Cereal", "Local Dishes"
            ),
            selectedCategory = state.selectedCategory,
            onSelectCategory = { category ->
                onSelectCategory(category)
            },
        )

        Spacer(modifier = Modifier.height(15.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(state.filteredRecipes) { recipe ->
                HomeRecipeCard(
                    recipe = recipe,
                    isSaved = recipe.isSaved
                )
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {
    HomeScreen(profilePainter = ColorPainter(Color.Gray))
}