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

    private fun observeBookmarks() {
        viewModelScope.launch {
            getBookmarkedRecipeIdsUseCase.execute().collect { ids ->
                _state.update { it.copy(bookmarkedRecipeIds = ids.toSet()) }
            }
        }
    }

    private fun toggleBookmark(recipeId: Int) {
        viewModelScope.launch {
            val isBookmarked = _state.value.bookmarkedRecipeIds.contains(recipeId)
            if (isBookmarked) {
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
                    _state.value = _state.value.copy(
                        allRecipes = recipes,
                        filteredRecipes = recipes,
                        newRecipes = recipes
                            .sortedByDescending { it.createdAt }
                            .take(5),
                        errorMessage = null
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        errorMessage = "Failed to load recipes"
                    )
            }
        }
    }

    fun onSelectCategory(category: String) {
        val filtered =
            if (category == "All") {
                _state.value.allRecipes
            } else {
                _state.value.allRecipes.filter { it.category == category }
            }

        _state.value = _state.value.copy(
            selectedCategory = category,
            filteredRecipes = filtered
        )
    }
}
