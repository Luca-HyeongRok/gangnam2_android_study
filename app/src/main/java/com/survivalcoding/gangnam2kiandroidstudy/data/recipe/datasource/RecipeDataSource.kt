package com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource

import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.dto.RecipeDTO

interface RecipeDataSource{
    suspend fun getRecipes(): List<RecipeDTO>
}