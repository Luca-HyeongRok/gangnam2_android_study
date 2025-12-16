package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.survivalcoding.gangnam2kiandroidstudy.AppApplication
import com.survivalcoding.gangnam2kiandroidstudy.R

@Composable
fun HomeRoot(
    onSearchClick: () -> Unit,
) {
    val app = LocalContext.current.applicationContext as AppApplication

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.factory(app)
    )

    val state = viewModel.state.collectAsState().value
    val profilePainter = painterResource(R.drawable.profile)

    HomeScreen(
        state = state,
        onSelectCategory = viewModel::onSelectCategory,
        profilePainter = profilePainter,
        onSearchClick = onSearchClick,
    )
}
