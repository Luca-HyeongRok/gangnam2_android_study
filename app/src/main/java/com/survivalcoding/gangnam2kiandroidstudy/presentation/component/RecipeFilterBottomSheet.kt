package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.FilterButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.RatingButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.SmallButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.CategoryFilter
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.RateFilter
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet.TimeFilter
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe.FilterSearchState
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeFilterBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    currentFilterState: FilterSearchState,
    onDismiss: () -> Unit = {},
    onApplyFilter: (FilterSearchState) -> Unit = {},
) {
    var localFilter by remember { mutableStateOf(currentFilterState) }

    ModalBottomSheet(
        dragHandle = null,
        containerColor = AppColors.white,
        onDismissRequest = { onDismiss() },
        sheetState = sheetState
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 타이틀
            Text(
                text = "Filter Search",
                style = AppTextStyles.smallTextBold,
                modifier = Modifier.padding(top = 31.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp),
            ) {
                // -------- Time --------
                Text(
                    text = "Time",
                    style = AppTextStyles.smallTextBold,
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    TimeFilter.values().forEach { option ->
                        FilterButton(
                            text = option.label,
                            isSelected = localFilter.time == option,
                            onClick = {
                                // TimeFilter는 nullable 아니니까 그냥 선택만 교체
                                localFilter = localFilter.copy(time = option)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // -------- Rate --------
                Text(
                    text = "Rate",
                    style = AppTextStyles.smallTextBold,
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RateFilter.values().forEach { option ->
                        RatingButton(
                            text = option.label,
                            isSelected = localFilter.rate == option,
                            onClick = {
                                // 같은 거 다시 누르면 해제, 아니면 선택
                                val newRate =
                                    if (localFilter.rate == option) null else option
                                localFilter = localFilter.copy(rate = newRate)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // -------- Category --------
                Text(
                    text = "Category",
                    style = AppTextStyles.smallTextBold,
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf(
                        CategoryFilter.ALL,
                        CategoryFilter.CEREAL,
                        CategoryFilter.VEGETABLES,
                        CategoryFilter.DINNER,
                    ).forEach { option ->
                        FilterButton(
                            text = option.label,
                            isSelected = localFilter.category == option,
                            onClick = {
                                val newCategory =
                                    if (localFilter.category == option) null else option
                                localFilter = localFilter.copy(category = newCategory)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf(
                        CategoryFilter.CHINESE,
                        CategoryFilter.LOCAL_DISH,
                        CategoryFilter.FRUIT,
                        CategoryFilter.BREAKFAST,
                    ).forEach { option ->
                        FilterButton(
                            text = option.label,
                            isSelected = localFilter.category == option,
                            onClick = {
                                val newCategory =
                                    if (localFilter.category == option) null else option
                                localFilter = localFilter.copy(category = newCategory)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    listOf(
                        CategoryFilter.SPANISH,
                        CategoryFilter.JAPANESE,
                        CategoryFilter.LUNCH,
                    ).forEach { option ->
                        FilterButton(
                            text = option.label,
                            isSelected = localFilter.category == option,
                            onClick = {
                                val newCategory =
                                    if (localFilter.category == option) null else option
                                localFilter = localFilter.copy(category = newCategory)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }

            // 하단 Filter 버튼
            SmallButton(
                text = "Filter",
                modifier = Modifier
                    .padding(bottom = 22.dp)
                    .padding(horizontal = 100.dp)
            ) {
                onApplyFilter(localFilter)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun RecipeFilterBottomSheetPreview() {
    RecipeFilterBottomSheet(
        sheetState = rememberModalBottomSheetState(),
        currentFilterState = FilterSearchState()
    )
}
