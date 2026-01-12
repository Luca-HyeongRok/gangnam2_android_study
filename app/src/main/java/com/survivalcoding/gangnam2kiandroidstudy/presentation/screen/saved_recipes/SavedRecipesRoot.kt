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
 * 기존 Compose 화면(SavedRecipesScreen)을 제거하고,
 * 레거시 Fragment(SavedRecipesLegacyFragment)를
 * AndroidView + FragmentContainerView를 통해 직접 호스팅한다.
 *
 *  Navigation/MainRoot 구조는 그대로 유지된다.
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
         * Compose 화면을 유지하면서 레거시 Fragment를 함께 보여준다.
         *
         * 동일한 데이터 소스를 사용하므로,
         * Compose에서 북마크 변경 시 레거시 리스트도 갱신된다.
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
         * FragmentContainerView를 Compose 아래에 배치해
         * 레거시 구조를 그대로 노출한다.
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
     * Fragment attach / detach 를 관리하는 영역
     *
     * DisposableEffect는
     * - 처음 진입 시 Fragment를 붙이고
     * - Composable이 제거될 때 Fragment를 정리한다.
     */
    DisposableEffect(activity, containerId, fragment) {

        /**
         * FragmentActivity가 아닌 경우*
         *  아무 것도 하지 않고 onDispose만 반환
         */
        if (activity == null) {
            onDispose { }
        } else {

            /**
             * 레거시 Fragment → Compose 로 이벤트를 전달하기 위한 콜백 주입
             *
             * 레거시 Fragment는 Activity에 의존하지 않고
             * Callback 인터페이스만 통해 상위로 이벤트를 전달한다.
             */
            fragment.setCallback(object : SavedRecipesCallback {
                override fun onRecipeClick(recipeId: Int, recipeTitle: String) {
                    // Compose Navigation으로 상세 화면 이동
                    onOpenRecipeDetailState.value(recipeId)
                }
            })

            val fragmentManager = activity.supportFragmentManager

            /**
             * Fragment가 아직 붙어있지 않다면 attach
             *
             * findFragmentById 체크를 하지 않으면
             * recomposition 시 Fragment가 중복 생성될 수 있다.
             */
            if (fragmentManager.findFragmentById(containerId) == null) {
                fragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(containerId, fragment)
                    .commit()
            }

            /**
             * Composable이 화면에서 제거될 때 호출
             *
             * - 콜백 해제
             * - Fragment 제거
             */
            onDispose {
                fragment.setCallback(null)

                val existing = fragmentManager.findFragmentById(containerId)
                if (existing != null) {
                    fragmentManager.beginTransaction()
                        .remove(existing)
                        .commit()
                }
            }
        }
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
