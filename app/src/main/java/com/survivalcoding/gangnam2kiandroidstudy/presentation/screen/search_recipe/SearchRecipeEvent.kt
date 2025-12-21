package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

sealed interface SearchRecipeEvent {
    data class ShowSnackBar(val message: String) : SearchRecipeEvent
}