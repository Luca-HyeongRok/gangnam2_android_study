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
        observeSavedRecipes()
    }

    /**
     * 북마크된 레시피 Flow를 단 한 번만 수집한다.
     *
     * - ViewModel 생명주기 동안 유지
     * - 재호출로 인한 다중 collect 방지
     */
    private fun observeSavedRecipes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getSavedRecipesUseCase.execute()
                .collectLatest { result ->
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
