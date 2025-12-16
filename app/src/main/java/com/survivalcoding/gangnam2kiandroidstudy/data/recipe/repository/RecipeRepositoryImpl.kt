package com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository

import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.RecipeDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.mapper.toModel
import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource
) : RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> {
        return dataSource.getRecipes().map { it.toModel() }
    }
}

