package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.FilterBox
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SearchInputField
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.card.SearchRecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles
import com.survivalcoding.gangnam2kiandroidstudy.BuildConfig

@Composable
fun SearchRecipeScreen(
    state: SearchRecipeState,
    onAction: (SearchRecipeAction) -> Unit,
) {
    val title = if (BuildConfig.ENV_NAME == "prod") {
        "Search recipe"
    } else {
        "Search recipe (${BuildConfig.ENV_NAME})"
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppColors.white
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 30.dp),
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(27.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Arrow Back Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            onAction(SearchRecipeAction.ClickBack)
                        }
                )

                Spacer(modifier = Modifier.width(69.dp))

                Text(
                    text = title,
                    style = AppTextStyles.mediumTextBold.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(17.dp))

            // Search + Filter
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchInputField(
                    modifier = Modifier.weight(1f),
                    value = state.searchKeyword,
                    placeholder = "Search Recipe",
                    onValueChange = { text ->
                        onAction(SearchRecipeAction.InputKeyword(text))
                    }
                )

                Spacer(modifier = Modifier.width(20.dp))

                FilterBox(
                    onClick = { onAction(SearchRecipeAction.ClickFilter) }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 필터 결과
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = state.searchText,
                    style = AppTextStyles.normalTextBold
                )
                Text(
                    text = state.filteredRecipesText,
                    style = AppTextStyles.smallerTextRegular.copy(
                        color = AppColors.gray3
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 서치 레시피 카드
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(state.filteredRecipes) { recipe ->
                    SearchRecipeCard(recipe = recipe)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchRecipeScreenPreview() {
    SearchRecipeScreen(
        state = SearchRecipeState(
            searchKeyword = "",
            allRecipes = emptyList(),
            filteredRecipes = listOf(
                Recipe(
                    id = 1,
                    name = "Grilled Chicken",
                    chef = "Chef John",
                    rating = 4.5,
                    category = "Dinner",
                    imageUrl = "",
                    createdAt = System.currentTimeMillis(),
                    time = "20",
                ),
                Recipe(
                    id = 2,
                    name = "Fresh Salad",
                    chef = "Anna",
                    rating = 4.2,
                    category = "Vegetables",
                    imageUrl = "",
                    createdAt = System.currentTimeMillis(),
                    time = "20",
                )
            ),
            filteredRecipesText = "2 results"
        ),
        onAction = {}
    )
}
