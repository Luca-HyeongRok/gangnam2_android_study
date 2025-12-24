package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavedRecipesRoot() {
    val viewModel: SavedRecipesViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value

    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        SavedRecipesScreen(
            state = state,
            listState = listState,
            onRemoveBookmark = viewModel::removeBookmark,
            modifier = Modifier.padding(padding)
        )
    }

    LaunchedEffect(listState, state.recipes) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisibleIndex =
                layoutInfo.visibleItemsInfo.lastOrNull()?.index

            lastVisibleIndex == layoutInfo.totalItemsCount - 1
        }
            .distinctUntilChanged()
            .collect { isAtBottom ->
                if (isAtBottom && state.recipes.isNotEmpty()) {
                    snackbarHostState.showSnackbar(
                        "마지막 레시피까지 확인했습니다"
                    )
                }
            }
    }
}
