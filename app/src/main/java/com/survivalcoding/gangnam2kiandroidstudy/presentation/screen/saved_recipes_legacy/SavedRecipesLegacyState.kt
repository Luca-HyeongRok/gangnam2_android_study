package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe

data class SavedRecipesLegacyState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
)
