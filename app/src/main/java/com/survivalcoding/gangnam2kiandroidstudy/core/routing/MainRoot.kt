package com.survivalcoding.gangnam2kiandroidstudy.core.routing

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.home.HomeRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.main.MainScreen
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes.SavedRecipesRoot

@Composable
fun MainRoot(
    onOpenSearch: () -> Unit,
) {
    val tabBackStack = rememberNavBackStack(Route.Home)


    MainScreen(
        backStack = tabBackStack,
        body = { modifier ->
            NavDisplay(
                modifier = modifier,
                backStack = tabBackStack,
                entryProvider = entryProvider {
                    entry<Route.Home> {
                        HomeRoot(
                            onSearchClick = onOpenSearch,
                        )
                    }
                    entry<Route.SavedRecipes> { SavedRecipesRoot() }
                    // entry<Route.Notifications> { NotificationsRoot() }
                    // entry<Route.Profile> { ProfileRoot() }
                }
            )
        }
    )
}
