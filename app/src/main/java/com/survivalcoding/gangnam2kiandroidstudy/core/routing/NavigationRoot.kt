package com.survivalcoding.gangnam2kiandroidstudy.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe.SearchRecipeRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in.SignInRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up.SignUpRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.splash.SplashRoot

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    val topLevelBackStack = rememberNavBackStack(Route.Splash)

    fun reset(route: Route) {
        topLevelBackStack.clear()
        topLevelBackStack.add(route)
    }

    fun push(route: Route) {
        topLevelBackStack.add(route)
    }

    fun pop() {
        if (topLevelBackStack.size > 1) {
            topLevelBackStack.removeAt(topLevelBackStack.lastIndex)
        }
    }

    NavDisplay(
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        backStack = topLevelBackStack,
        entryProvider = entryProvider {

            entry<Route.Splash> {
                SplashRoot(
                    onStartClick = { reset(Route.SignIn) }
                )
            }

            entry<Route.SignIn> {
                SignInRoot(
                    onSignUpClick = { reset(Route.SignUp) },
                    onSignInSuccess = { reset(Route.Main) }
                )
            }

            entry<Route.SignUp> {
                SignUpRoot(
                    onBackToSignIn = { reset(Route.SignIn) },
                    onSignUpSuccess = { reset(Route.SignIn) }
                )
            }

            // 탭 화면들만
            entry<Route.Main> {
                MainRoot(
                    onOpenSearch = { push(Route.SearchRecipes) }
                )
            }

            // top-level로 올렸다가 pop으로 내림
            entry<Route.SearchRecipes> {
                SearchRecipeRoot(
                    onBackClick = { pop() }
                )
            }
        }
    )
}
