package com.survivalcoding.gangnam2kiandroidstudy.core.routing

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home.HomeRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.main.MainScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes.SavedRecipesRoot


@Composable
fun MainRoot(
    route: Route.Main, // Route.Main data class를 직접 받도록 변경
    onOpenSearch: () -> Unit,
    onOpenRecipeDetail: (Int) -> Unit,
) {
    // 딥링크로 전달받은 startTab으로 탭 내비게이션 스택을 초기화
    val tabBackStack = rememberNavBackStack(route.startTab)

    MainScreen(
        backStack = tabBackStack,
        body = { modifier ->
            NavDisplay(
                modifier = modifier,
                backStack = tabBackStack,
                entryProvider = entryProvider {
                    entry<Route.Home> {
                        HomeRoot(
                            onOpenSearch = onOpenSearch,
                        )
                    }
                    entry<Route.SavedRecipes> {
                        SavedRecipesRoot(
                            onOpenRecipeDetail = onOpenRecipeDetail,
                        )
                    }
                    entry<Route.Notifications> {
                        Text("Notifications – Coming Soon")
                    }
                    entry<Route.Profile> {
                        Text("Profile – Coming Soon")
                    }
                }
            )
        }
    )
}
