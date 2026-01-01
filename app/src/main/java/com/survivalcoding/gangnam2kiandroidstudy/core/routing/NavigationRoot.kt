package com.survivalcoding.gangnam2kiandroidstudy.core.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.detail.RecipeDetailRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe.SearchRecipeRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_in.SignInRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.sign_up.SignUpRoot
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.splash.SplashRoot

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    deepLinkUri: String? = null,
    onDeepLinkHandled: () -> Unit = {},
) {
    val backStack = rememberNavBackStack(Route.Splash)

    LaunchedEffect(deepLinkUri) {
        if (deepLinkUri == null) return@LaunchedEffect

        val uri = deepLinkUri.toUri()
        val host = uri.host
        val path = uri.path

        when {
            host == "luca-food.web.app" && path == "/recipes/saved" -> {
                backStack.clear()
                backStack.add(Route.Main(startTab = Route.SavedRecipes))
            }

            host == "luca-food.web.app" && path?.startsWith("/recipes/detail/") == true -> {
                val id = uri.pathSegments.getOrNull(2)?.toIntOrNull()
                if (id != null) {
                    backStack.clear()
                    backStack.add(Route.Main())
                    backStack.add(Route.RecipeDetail(id))
                }
            }

            host == "recipes" && path == "/saved" -> {
                backStack.clear()
                backStack.add(Route.Main(startTab = Route.SavedRecipes))
            }

            host == "recipes" && path?.startsWith("/detail/") == true -> {
                val id = uri.lastPathSegment?.toIntOrNull()
                if (id != null) {
                    backStack.clear()
                    backStack.add(Route.Main())
                    backStack.add(Route.RecipeDetail(id))
                }
            }
        }

        onDeepLinkHandled()
    }


    fun reset(route: Route) {
        backStack.clear()
        backStack.add(route)
    }

    fun push(route: Route) {
        backStack.add(route)
    }

    fun pop() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {

            entry<Route.Splash> {
                SplashRoot(
                    onStartClick = { reset(Route.SignIn) }
                )
            }

            entry<Route.SignIn> {
                SignInRoot(
                    onSignUpClick = { reset(Route.SignUp) },
                    onSignInSuccess = { reset(Route.Main()) }
                )
            }

            entry<Route.SignUp> {
                SignUpRoot(
                    onBackToSignIn = { reset(Route.SignIn) },
                    onSignUpSuccess = { reset(Route.SignIn) }
                )
            }

            entry<Route.Main> { route ->
                MainRoot(
                    route = route,
                    onOpenSearch = { push(Route.SearchRecipes) },
                    onOpenRecipeDetail = { id ->
                        push(Route.RecipeDetail(id))
                    }
                )
            }

            entry<Route.SearchRecipes> {
                SearchRecipeRoot(onBack = { pop() })
            }

            entry<Route.RecipeDetail> { route ->
                RecipeDetailRoot(
                    recipeId = route.recipeId,
                    onBack = { pop() }
                )
            }
        }
    )
}
