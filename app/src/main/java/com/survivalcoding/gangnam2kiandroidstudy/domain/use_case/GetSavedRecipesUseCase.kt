package com.survivalcoding.gangnam2kiandroidstudy.domain.use_case

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.SavedRecipesRepository

class GetSavedRecipesUseCase(
    private val bookmarkRepository: BookmarkRepository,
    private val savedRecipesRepository: SavedRecipesRepository,
) {
    suspend fun execute(): Result<List<Recipe>> {
        val ids = bookmarkRepository.getSavedRecipeIds()
        return Result.success(savedRecipesRepository.getRecipesByIds(ids))
    }
}

