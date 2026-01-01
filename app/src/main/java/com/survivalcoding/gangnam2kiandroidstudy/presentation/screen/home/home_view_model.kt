package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.RecipeRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.AddBookmarkUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetBookmarkedRecipeIdsUseCase
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.RemoveBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RecipeRepository,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val getBookmarkedRecipeIdsUseCase: GetBookmarkedRecipeIdsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        observeBookmarks()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.LoadHome -> {
                loadRecipes()
            }

            is HomeAction.ClickCategory -> {
                onSelectCategory(action.category)
            }

            is HomeAction.ToggleRecipeBookmark -> {
                toggleBookmark(action.recipeId)
            }

            else -> Unit
        }
    }

    /**
     * DB / DataSource의 최종 북마크 상태를 동기화
     * → 단일 소스 오브 트루스 역할
     */
    private fun observeBookmarks() {
        viewModelScope.launch {
            getBookmarkedRecipeIdsUseCase.execute().collect { ids ->
                _state.update {
                    it.copy(bookmarkedRecipeIds = ids.toSet())
                }
            }
        }
    }

    /**
     * 북마크 토글
     *
     * - UI 상태를 먼저 변경 (Optimistic Update)
     * - UseCase는 명령만 수행
     * - Flow에서 최종 상태 재동기화
     */
    private fun toggleBookmark(recipeId: Int) {
        viewModelScope.launch {
            val currentlyBookmarked =
                _state.value.bookmarkedRecipeIds.contains(recipeId)

            // 1. 로컬 상태 즉시 반영
            _state.update { state ->
                val updatedIds =
                    if (currentlyBookmarked) {
                        state.bookmarkedRecipeIds - recipeId
                    } else {
                        state.bookmarkedRecipeIds + recipeId
                    }

                state.copy(bookmarkedRecipeIds = updatedIds)
            }

            // 2. UseCase는 단일 명령만 수행
            if (currentlyBookmarked) {
                removeBookmarkUseCase.execute(recipeId)
            } else {
                addBookmarkUseCase.execute(recipeId)
            }
        }
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            runCatching { repository.getRecipes() }
                .onSuccess { recipes ->
                    _state.update {
                        it.copy(
                            allRecipes = recipes,
                            filteredRecipes = recipes,
                            newRecipes = recipes
                                .sortedByDescending { recipe -> recipe.createdAt }
                                .take(5),
                            errorMessage = null
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(errorMessage = "Failed to load recipes")
                    }
                }
        }
    }

    private fun onSelectCategory(category: String) {
        val filtered =
            if (category == "All") {
                _state.value.allRecipes
            } else {
                _state.value.allRecipes.filter { it.category == category }
            }

        _state.update {
            it.copy(
                selectedCategory = category,
                filteredRecipes = filtered
            )
        }
    }
}
