package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

import android.os.Bundle
import android.widget.Toast
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

        // Fragment가 이미 존재하는지 확인
        val fragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer)
                as? SavedRecipesLegacyFragment

        if (fragment == null) {
            // 최초 생성 시 Fragment 추가
            val newFragment = SavedRecipesLegacyFragment()

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(
                    R.id.fragmentContainer,
                    newFragment
                )
                .commit()

            // 최초 생성 Fragment에 콜백 설정
            newFragment.setCallback(savedRecipesCallback)

        } else {
            // 재생성된 Fragment에 콜백 재연결
            fragment.setCallback(savedRecipesCallback)
        }
    }

    /**
     * Fragment에서 사용하는 콜백 구현
     *
     * - Activity 재생성 시에도 항상 다시 주입됨
     * - Fragment 내부 callback null 문제 방지
     */
    private val savedRecipesCallback = object : SavedRecipesCallback {
        override fun onRecipeClick(
            recipeId: Int,
            recipeTitle: String
        ) {
            openRecipeDetail(recipeId, recipeTitle)
        }
    }

    /**
     * Fragment로부터 "상세 화면으로 이동하고 싶다"는 요청을 받는 함수
     *
     * Fragment는 Activity의 구현을 몰라야 하므로
     * 이동 로직은 Activity에만 존재한다.
     */
    fun openRecipeDetail(recipeId: Int, recipeTitle: String) {
        Toast.makeText(
            this,
            "Activity에서 받은 클릭: $recipeId / $recipeTitle",
            Toast.LENGTH_SHORT
        ).show()
    }
}
