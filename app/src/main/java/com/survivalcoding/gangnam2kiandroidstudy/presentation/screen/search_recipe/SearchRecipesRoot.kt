package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.FilterSearchBottomSheet
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRecipeRoot(
    onBack: () -> Unit,
    viewModel: SearchRecipeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // BottomSheet
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // SnackBar
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.onAction(SearchRecipeAction.Load)
    }

    // 스낵바 수집
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is SearchRecipeEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        SearchRecipeScreen(
            state = state,
            onAction = { action ->
                when (action) {

                    // 뒤로가기
                    SearchRecipeAction.ClickBack -> {
                        onBack()
                    }

                    // 필터 클릭
                    SearchRecipeAction.ClickFilter -> {
                        isBottomSheetOpen = true
                    }

                    // 나머지는 ViewModel
                    else -> {
                        viewModel.onAction(action)
                    }
                }
            }
        )
    }

    // Filter BottomSheet
    if (isBottomSheetOpen) {
        FilterSearchBottomSheet(
            sheetState = sheetState,
            state = state.filterState,

            // 선택 변경
            onChange = viewModel::updateFilterSearchState,
            /*
            onChange = { newFilterState ->
                viewModel.updateFilterSearchState(newFilterState)
            },
             */

            // 취소
            onDismiss = {
                viewModel.onAction(
                    SearchRecipeAction.CancelFilter(
                        filterState = state.filterState
                    )
                )
                isBottomSheetOpen = false
            },

            // 적용
            onApplyFilter = {
                viewModel.onAction(SearchRecipeAction.ApplyFilter)
                isBottomSheetOpen = false
            }
        )
    }
}

