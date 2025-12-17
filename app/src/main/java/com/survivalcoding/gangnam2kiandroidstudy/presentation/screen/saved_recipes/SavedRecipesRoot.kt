package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication

@Composable
fun SavedRecipesRoot() {
    val app = LocalContext.current.applicationContext as AppApplication

    val viewModel: SavedRecipesViewModel = viewModel(
        factory = SavedRecipesViewModel.factory(app)
    )

    val state = viewModel.state.collectAsState().value

    SavedRecipesScreen(
        state = state,
        onRefresh = viewModel::loadSavedRecipes,
        onRemoveBookmark = viewModel::removeBookmark
    )
}
