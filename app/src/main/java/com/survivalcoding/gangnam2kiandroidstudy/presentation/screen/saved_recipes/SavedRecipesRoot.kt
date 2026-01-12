package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes

import android.util.Log
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy.SavedRecipesCallback
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy.SavedRecipesLegacyFragment

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

    Text("Legacy")
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

    /**
     * Fragment를 담을 실제 Android View
     *
     * Compose 화면 안에 FragmentContainerView를 생성한다.
     */
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            FragmentContainerView(ctx).apply {
                id = containerId
            }
        }
    )

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
                Log.d(
                    "SavedRecipesRoot",
                    "Hosting legacy fragment in containerId=$containerId"
                )
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
}
