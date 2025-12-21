package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

sealed interface SearchRecipeAction {

    // 화면 진입
    object Load : SearchRecipeAction

    // 검색어 입력
    data class InputKeyword(
        val keyword: String
    ) : SearchRecipeAction

    // 필터 버튼 클릭
    object ClickFilter : SearchRecipeAction

    // 필터 적용
    object ApplyFilter : SearchRecipeAction

    // 필터 취소
    data class CancelFilter(
        val filterState: FilterSearchState
    ) : SearchRecipeAction

    // 뒤로가기
    object ClickBack : SearchRecipeAction
}
