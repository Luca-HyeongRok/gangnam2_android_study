package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe

data class SavedRecipeState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
)