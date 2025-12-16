package com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository

import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe

interface RecipeRepository{
    suspend fun getRecipes(): List<Recipe>
    suspend fun getSavedRecipes(): List<Recipe> = getRecipes().filter { it.isSaved }
}
