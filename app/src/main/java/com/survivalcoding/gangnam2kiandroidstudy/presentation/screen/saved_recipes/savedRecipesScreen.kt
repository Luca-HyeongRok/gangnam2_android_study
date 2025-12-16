package com.survivalcoding.gangnam2kiandroidstudy.presentation.savedrecipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes.SavedRecipesViewModel
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun SavedRecipesScreen(
    viewModel: SavedRecipesViewModel = viewModel(
        factory = SavedRecipesViewModel.factory(
            LocalContext.current.applicationContext as AppApplication
        )
    )
) {
    val recipes = viewModel.recipes.collectAsState().value


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(54.dp))

        Text(
            text = "Saved Recipes",
            style = AppTextStyles.mediumTextBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
        ) {
            items(recipes) { recipe ->
                RecipeCard(
                    recipe = recipe,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SavedRecipesScreenPreview() {
    SavedRecipesScreen()
}
