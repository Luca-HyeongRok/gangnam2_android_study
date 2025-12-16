package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe

import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.CategoryFilter
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.RateFilter
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.TimeFilter

data class FilterSearchState(
    val time: TimeFilter = TimeFilter.ALL,
    val rate: RateFilter? = null,
    val category: CategoryFilter? = null
)
