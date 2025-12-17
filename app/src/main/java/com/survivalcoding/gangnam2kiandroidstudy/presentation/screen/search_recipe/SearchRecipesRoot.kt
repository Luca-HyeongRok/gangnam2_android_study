package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.FilterSearchBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRecipeRoot(
    onBackClick: () -> Unit,
) {
    val viewModel: SearchRecipeViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    var isBottomSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // 메인 화면
    SearchRecipeScreen(
        state = state,
        onSearchKeywordChange = viewModel::updateSearchQuery,
        onFilterClick = { isBottomSheetOpen = true },
        onBackClick = onBackClick,
    )

    // 바텀시트
    if (isBottomSheetOpen) {
        FilterSearchBottomSheet(
            sheetState = sheetState,
            state = state.filterState,
            onChange = viewModel::updateFilterSearchState,
            onDismiss = { isBottomSheetOpen = false },
            onApplyFilter = {
                viewModel.applyFilter()
                isBottomSheetOpen = false
            }
        )
    }
}
