package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SavedRecipesRoot() {
    val viewModel: SavedRecipesViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value

    SavedRecipesScreen(
        state = state,
        onRefresh = viewModel::loadSavedRecipes,
        onRemoveBookmark = viewModel::removeBookmark
    )
}
