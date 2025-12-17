package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.survivalcoding.gangnam2kiandroidstudy.R

@Composable
fun HomeRoot(
    onSearchClick: () -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState().value
    val profilePainter = painterResource(R.drawable.profile)

    HomeScreen(
        state = state,
        onSelectCategory = viewModel::onSelectCategory,
        profilePainter = profilePainter,
        onSearchClick = onSearchClick,
    )
}
