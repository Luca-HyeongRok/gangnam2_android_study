package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetSavedRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedRecipesViewModel(
    private val getSavedRecipesUseCase: GetSavedRecipesUseCase,
    private val bookmarkRepository: BookmarkRepository, // 북마크 취소용
) : ViewModel() {

    private val _state = MutableStateFlow(SavedRecipeState())
    val state: StateFlow<SavedRecipeState> = _state.asStateFlow()

    init {
        loadSavedRecipes()
    }

    fun loadSavedRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = getSavedRecipesUseCase.execute()
            val recipes = result.getOrElse { emptyList() }

            _state.update {
                it.copy(
                    isLoading = false,
                    recipes = recipes
                )
            }
        }
    }

    fun removeBookmark(id: Int) {
        viewModelScope.launch {
            bookmarkRepository.removeSavedRecipeId(id)
            loadSavedRecipes()
        }
    }

    companion object {
        fun factory(application: AppApplication): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SavedRecipesViewModel(
                    getSavedRecipesUseCase = application.getSavedRecipesUseCase,
                    bookmarkRepository = application.bookmarkRepository,
                )
            }
        }
    }
}