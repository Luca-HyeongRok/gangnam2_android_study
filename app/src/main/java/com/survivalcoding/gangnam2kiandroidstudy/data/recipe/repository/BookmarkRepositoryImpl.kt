package com.survivalcoding.gangnam2kiandroidstudy.data.recipe.repository

import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository

class BookmarkRepositoryImpl(
    private val recipeRepository: RecipeRepository,
) : BookmarkRepository {

    private val savedIds = mutableSetOf<Int>()
    private var initialized = false

    override suspend fun getSavedRecipeIds(): List<Int> {
        if (!initialized) {
            val ids = recipeRepository.getRecipes()
                .filter { it.isSaved }
                .map { it.id }
            savedIds.clear()
            savedIds.addAll(ids)
            initialized = true
        }
        return savedIds.toList()
    }

    override suspend fun removeSavedRecipeId(id: Int) {
        savedIds.remove(id)
        recipeRepository.updateSaved(id, false)
    }
}
