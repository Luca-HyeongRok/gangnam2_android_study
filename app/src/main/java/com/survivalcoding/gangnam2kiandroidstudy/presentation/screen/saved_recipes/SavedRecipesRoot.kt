package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy.SavedRecipesCallback
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy.SavedRecipesLegacyFragment
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.koinViewModel

/**
 * SavedRecipesRoot
 *
 * Compose 기반 SavedRecipes 탭의 진입점.
 *
 * 이 화면은 **Compose UI(SavedRecipesScreen)** 와
 * **레거시 Fragment(SavedRecipesLegacyFragment)** 를
 * 동시에 노출하는 하이브리드 구조이다.
 *
 * - 상단: Compose 기반 SavedRecipesScreen
 * - 하단: AndroidView + FragmentContainerView를 통한 레거시 RecyclerView
 *
 * 두 화면은 동일한 ViewModel / Repository를 공유하므로,
 * 한쪽에서 북마크 변경 시 다른 쪽도 자동으로 갱신된다.
 *
 * Navigation / MainRoot 구조는 그대로 유지된다.
 */
@Composable
fun SavedRecipesRoot(
    onOpenRecipeDetail: (Int) -> Unit,
) {
    // Compose 화면은 기존 ViewModel/UseCase 체계를 그대로 유지한다.
    val viewModel: SavedRecipesViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value

    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    /**
     * FragmentManager를 사용하기 위해
     * Context를 FragmentActivity로 캐스팅
     */
    val activity = context as? FragmentActivity

    /**
     * FragmentContainerView에 사용할 고유 ID
     * (Compose recomposition 시에도 유지되도록 remember 사용)
     */
    val containerId = remember { View.generateViewId() }

    /**
     * 레거시 Fragment 인스턴스
     * Compose 재구성 시 재생성되지 않도록 remember
     */
    val fragment = remember { SavedRecipesLegacyFragment() }

    /**
     * 최신 onOpenRecipeDetail 람다를 안전하게 참조하기 위함
     * (recomposition 대응)
     */
    val onOpenRecipeDetailState = rememberUpdatedState(onOpenRecipeDetail)

    Column(modifier = Modifier.fillMaxSize()) {

        /**
         * Compose 기반 SavedRecipesScreen 영역
         */
        Scaffold(
            modifier = Modifier.weight(1f),
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { padding ->
            SavedRecipesScreen(
                state = state,
                listState = listState,
                onRemoveBookmark = viewModel::removeBookmark,
                onCardClick = onOpenRecipeDetail,
                modifier = Modifier.padding(padding)
            )
        }

        /**
         * 레거시 RecyclerView 영역
         *
         * FragmentContainerView를 Compose 하단에 배치하여
         * 기존 Fragment + RecyclerView 구조를 그대로 노출한다.
         */
        AndroidView(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            factory = { ctx ->
                FragmentContainerView(ctx).apply {
                    id = containerId
                }
            }
        )
    }

    /**
     * Fragment attach / detach 관리
     *
     * DisposableEffect는
     * - 최초 진입 시 Fragment를 attach
     * - Composable 제거 시 Fragment를 detach 한다.
     */
    DisposableEffect(activity, containerId, fragment) {

        if (activity == null) {
            onDispose { }
        } else {

            /**
             * 레거시 Fragment → Compose 로 이벤트 전달용 콜백 주입
             */
            fragment.setCallback(object : SavedRecipesCallback {
                override fun onRecipeClick(recipeId: Int, recipeTitle: String) {
                    onOpenRecipeDetailState.value(recipeId)
                }
            })

            val fragmentManager = activity.supportFragmentManager

            if (fragmentManager.findFragmentById(containerId) == null) {
                fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(containerId, fragment)
                    .commit()
            }

            onDispose {
                fragment.setCallback(null)

                fragmentManager.findFragmentById(containerId)?.let {
                    fragmentManager.beginTransaction()
                        .remove(it)
                        .commit()
                }
            }
        }
    }

    /**
     * 리스트 하단 도달 시 Snackbar 표시
     */
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
