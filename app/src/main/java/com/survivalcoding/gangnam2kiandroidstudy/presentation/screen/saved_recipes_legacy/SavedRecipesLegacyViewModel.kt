package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.survivalcoding.gangnam2kiandroidstudy.domain.repository.BookmarkRepository
import com.survivalcoding.gangnam2kiandroidstudy.domain.use_case.GetSavedRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedRecipesLegacyViewModel(
    private val getSavedRecipesUseCase: GetSavedRecipesUseCase,
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SavedRecipesLegacyState())
    val state: StateFlow<SavedRecipesLegacyState> = _state.asStateFlow()

    init {
        loadSavedRecipes()
    }

    fun loadSavedRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Compose/Legacy가 동일한 BookmarkRepository를 공유하므로,
            // Compose에서 북마크가 변경되면 이 Flow가 자동으로 갱신된다.
            getSavedRecipesUseCase.execute().collectLatest { result ->
                val recipes = result.getOrElse { emptyList() }
                _state.update {
                    it.copy(
                        isLoading = false,
                        recipes = recipes
                    )
                }
            }
        }
    }

    fun removeBookmark(id: Int) {
        viewModelScope.launch {
            bookmarkRepository.removeSavedRecipeId(id)
        }
    }
}
