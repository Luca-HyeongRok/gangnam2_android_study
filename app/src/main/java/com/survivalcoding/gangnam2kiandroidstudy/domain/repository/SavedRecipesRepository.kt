package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe

interface SavedRecipesRepository {
    suspend fun getRecipesByIds(ids: List<Int>): List<Recipe>
    suspend fun removeSavedRecipe(id: Int)
}
