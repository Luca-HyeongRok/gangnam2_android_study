package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.FilterBox
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SearchInputField
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.SearchRecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun SearchRecipeScreen(
    state: SearchRecipeState,
    onSearchKeywordChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = AppColors.white
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Search recipes",
                style = AppTextStyles.mediumTextBold
            )

            Spacer(modifier = Modifier.height(17.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchInputField(
                    modifier = Modifier.weight(1f),
                    value = state.searchKeyword,
                    placeholder = "Search Recipe",
                    onValueChange = onSearchKeywordChange
                )

                Spacer(modifier = Modifier.width(20.dp))

                FilterBox(
                    onClick = onFilterClick
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

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
        onSearchKeywordChange = {},
        onFilterClick = {},
        onBackClick = {},
    )
}
