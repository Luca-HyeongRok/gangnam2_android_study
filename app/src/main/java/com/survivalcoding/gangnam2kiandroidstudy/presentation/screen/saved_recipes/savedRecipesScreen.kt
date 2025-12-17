package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun SavedRecipesScreen(
    state: SavedRecipeState,
    onRefresh: () -> Unit,
    onRemoveBookmark: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(54.dp))

        Text(text = "Saved Recipes", style = AppTextStyles.mediumTextBold)

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        ) {
            items(
                items = state.recipes,
                key = { it.id }
            ) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    modifier = Modifier.padding(vertical = 10.dp),
                    onBookmarkClick = { onRemoveBookmark(recipe.id) }
                )
            }
        }
    }
}
