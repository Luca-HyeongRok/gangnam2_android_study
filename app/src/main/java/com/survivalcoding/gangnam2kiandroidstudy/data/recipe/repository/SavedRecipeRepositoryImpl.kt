package com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.SavedRecipesRepository

class SavedRecipesRepositoryImpl(
    private val recipeRepository: RecipeRepository,
) : SavedRecipesRepository {

    override suspend fun getRecipesByIds(ids: List<Int>): List<Recipe> {
        return recipeRepository.getRecipes()
            .filter { it.id in ids }
    }

    override suspend fun removeSavedRecipe(id: Int) {
        recipeRepository.updateSaved(id, false)
    }
}
