package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.survivalcoding.gangnam2kiandroidstudy.R

/**
 * 레거시 RecyclerView 기반 화면의 진입점 Activity
 *
 * Compose Navigation 흐름과 분리하여
 * Activity + Fragment + RecyclerView 구조를 명확히 보여주기 위함
 *
 * 이 Activity는 UI를 직접 그리지 않고,
 * Fragment를 담는 "호스트(컨테이너)" 역할만 수행한다.
 */
class SavedRecipesLegacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_recipes_legacy)

        /**
         * savedInstanceState == null 체크 이유
         *
         * - 화면 회전, 다크모드 변경 등으로 Activity가 재생성될 수 있다.
         * - 이때 FragmentManager는 기존 Fragment를 자동으로 복원한다.
         *
         * 만약 이 조건 없이 항상 Fragment를 replace 하면,
         * → 동일한 Fragment가 중복으로 추가되는 문제가 발생한다.
         *
         * 따라서 최초 생성 시에만 Fragment를 추가하도록
         * savedInstanceState를 반드시 확인해야 한다.
         */
        if (savedInstanceState == null) {

            /**
             * FragmentTransaction 구성
             *
             * setReorderingAllowed(true)
             * - Fragment 상태 복원 및 트랜잭션 재정렬을 최적화한다.
             * - Jetpack 권장 옵션으로, Fragment 재생성 이슈를 줄이는 데 도움을 준다.
             *
             * replace()
             * - fragmentContainer 안의 기존 Fragment를 제거하고
             *   SavedRecipesLegacyFragment로 교체한다.
             */
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(
                    R.id.fragmentContainer,
                    SavedRecipesLegacyFragment().apply {
                        setCallback(object : SavedRecipesCallback {
                            override fun onRecipeClick(
                                recipeId: Int,
                                recipeTitle: String
                            ) {
                                openRecipeDetail(recipeId, recipeTitle)
                            }
                        })
                    }
                )
                .commit()
        }
    }
    /**
     * Fragment로부터 "상세 화면으로 이동하고 싶다"는 요청을 받는 함수
     *
     * Fragment는 Activity의 구현을 몰라야 하므로
     * 이동 로직은 Activity에만 존재한다.
     */
    fun openRecipeDetail(recipeId: Int, recipeTitle: String) {
        // 지금은 구조만 보여주기 위한 로그/토스트 단계
        android.widget.Toast.makeText(
            this,
            "Activity에서 받은 클릭: $recipeId / $recipeTitle",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }
}
