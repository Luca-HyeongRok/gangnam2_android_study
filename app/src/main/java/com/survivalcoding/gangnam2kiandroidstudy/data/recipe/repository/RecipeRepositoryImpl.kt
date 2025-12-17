package com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository

import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.datasource.RecipeDataSource
import com.survivalcoding.gangnam2kiandroidstudy.data.recipe.mapper.toModel
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe

class RecipeRepositoryImpl(
    private val dataSource: RecipeDataSource
) : RecipeRepository {

    // 앱 실행 중 유지되는 캐시
    private var cachedRecipes: List<Recipe> = emptyList()

    override suspend fun getRecipes(): List<Recipe> {
        if (cachedRecipes.isEmpty()) {
            cachedRecipes = dataSource.getRecipes()
                .map { it.toModel() }
        }
        return cachedRecipes
    }

    override suspend fun updateSaved(id: Int, isSaved: Boolean) {
        cachedRecipes = cachedRecipes.map { recipe ->
            if (recipe.id == id) {
                recipe.copy(isSaved = isSaved)
            } else {
                recipe
            }
        }
    }
}
