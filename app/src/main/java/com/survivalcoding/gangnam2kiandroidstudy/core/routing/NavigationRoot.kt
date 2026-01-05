package com.survivalcoding.gangnam2kiandroidstudy.core.routing

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.survivalcoding.gangnam2kiandroidstudy.auth.AuthStateHolder
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.AuthState
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
    // Firebase Auth 상태 리스너 등록 / 해제
    DisposableEffect(Unit) {
        AuthStateHolder.startListening()
        onDispose {
            AuthStateHolder.stopListening()
        }
    }

    // 인증 상태 구독
    val authState by AuthStateHolder.authState.collectAsState()

    // ✅ 이 로그를 반드시 추가
    LaunchedEffect(authState) {
        Log.d("NavigationRoot", "authState = $authState")
    }

    // 인증 상태 변경 시 루트 화면 전환
    Crossfade(
        targetState = authState,
        label = "Root Animation"
    ) { state ->
        when (state) {
            AuthState.Authenticated -> {
                Log.d("NavigationRoot", "→ MainNavigation") // ✅ 이것도

                MainNavigation(
                    modifier = modifier,
                    deepLinkUri = deepLinkUri,
                    onDeepLinkHandled = onDeepLinkHandled
                )
            }

            AuthState.Unauthenticated,
            AuthState.Unknown -> {
                Log.d("NavigationRoot", "→ AuthNavigation") // ✅ 이것도

                AuthNavigation(modifier = modifier)
            }
        }
    }
}

@Composable
private fun AuthNavigation(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(Route.Splash)

    fun reset(route: Route) {
        backStack.clear()
        backStack.add(route)
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
                    onNavigateToSignUp = { reset(Route.SignUp) },
                    onNavigateToHome = {
                        // 실제 네비게이션은 AuthStateHolder가 처리하므로 비워둬도 OK
                        // reset(Route.Main)하면 오히려 중복 이동 위험
                    }
                )
            }

            entry<Route.SignUp> {
                SignUpRoot(
                    onLoginClick = { reset(Route.SignIn) },
                    onSignUpSuccess = { reset(Route.SignIn) }
                )
            }
        }
    )
}

@Composable
private fun MainNavigation(
    modifier: Modifier = Modifier,
    deepLinkUri: String? = null,
    onDeepLinkHandled: () -> Unit,
) {
    val backStack = rememberNavBackStack(Route.Main())

    // 인증된 사용자만 딥링크 처리
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
