package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

/**
 * SavedRecipesCallback
 *
 * SavedRecipesLegacyFragment가 외부로 이벤트를 전달하기 위한 계약(Contract)
 *
 * - Fragment는 "무엇이 클릭되었는지"만 알린다
 * - 실제 처리(네비게이션/토스트/로그)는 호스트가 결정한다
 *
 * 언제든 다른 UI(Compose, Activity, 다른 Fragment)로
 * 호스트를 교체할 수 있도록 분리된 인터페이스
 */
interface SavedRecipesCallback {
    fun onRecipeClick(recipeId: Int, recipeTitle: String)
}
