package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home

import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe

data class HomeState(
    val selectedCategory: String = "All",
    val allRecipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList()
)
