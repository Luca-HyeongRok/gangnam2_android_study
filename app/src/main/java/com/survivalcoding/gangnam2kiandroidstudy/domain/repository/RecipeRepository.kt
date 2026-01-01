package com.survivalcoding.gangnam2kiandroidstudy.domain.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe

interface RecipeRepository{
    suspend fun getRecipes(): List<Recipe>
    suspend fun getSavedRecipes(): List<Recipe> = getRecipes().filter { it.isSaved }
    suspend fun updateSaved(id: Int, isSaved: Boolean)
    suspend fun getRecipeById(recipeId: Int): Recipe?
}