package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe

data class SearchRecipeState(
    val searchKeyword: String = "",
    val allRecipes: List<Recipe> = emptyList(),
    val filteredRecipes: List<Recipe> = emptyList(),
    val searchText: String = "Search Result",
    val filteredRecipesText: String = "0 results",
    val isLoading: Boolean = false,
    val filterState: FilterSearchState = FilterSearchState()
)
