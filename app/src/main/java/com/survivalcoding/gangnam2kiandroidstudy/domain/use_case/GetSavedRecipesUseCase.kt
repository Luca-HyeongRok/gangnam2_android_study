package com.survivalcoding.gangnam2kiandroidstudy.domain.use_case

import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.SavedRecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetSavedRecipesUseCase(
    private val bookmarkRepository: BookmarkRepository,
    private val savedRecipesRepository: SavedRecipesRepository,
) {
    fun execute(): Flow<Result<List<Recipe>>> {
        return bookmarkRepository.getBookmarkedRecipeIds()
            .flatMapLatest { ids ->
                flow {
                    try {
                        val recipes = savedRecipesRepository.getRecipesByIds(ids)
                        emit(Result.success(recipes))
                    } catch (e: Exception) {
                        emit(Result.failure(e))
                    }
                }
            }
    }
}

