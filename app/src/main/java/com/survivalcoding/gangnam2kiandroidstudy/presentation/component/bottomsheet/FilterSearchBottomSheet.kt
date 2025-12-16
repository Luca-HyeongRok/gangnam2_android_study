package com.survivalcoding.gangnam2kiandroidstudy.presentation.component.bottomsheet

import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.FilterButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.RatingButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.SmallButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.search_recipe.FilterSearchState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.rememberModalBottomSheetState
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSearchBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    state: FilterSearchState,
    onChange: (FilterSearchState) -> Unit,
    onDismiss: () -> Unit,
    onApplyFilter: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier,
        containerColor = AppColors.white,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp),
        dragHandle = null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Filter Search",
                style = AppTextStyles.smallTextBold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            // TIME SECTION
            SectionTitle("Time")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                TimeFilter.entries.forEach { option ->
                    FilterButton(
                        modifier = modifier.padding(end = 10.dp),
                        text = option.label,
                        isSelected = state.time == option,
                        onClick = {
                            onChange(state.copy(time = option))
                        }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // RATE SECTION
            SectionTitle("Rate")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                RateFilter.entries.forEach { option ->
                    RatingButton(
                        text = option.label,
                        isSelected = state.rate == option,
                        onClick = {
                            val newRate = if (state.rate == option) null else option // 별점 클릭하면 돌아갈 방법X 그래서 추가
                            onChange(state.copy(rate = newRate))
                        }
                    )
                }
            }


            Spacer(Modifier.height(20.dp))

            // CATEGORY SECTION
            SectionTitle("Category")
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
            ) {
                CategoryFilter.entries.forEach { option ->
                    FilterButton(
                        modifier = modifier.padding(bottom = 10.dp, end = 10.dp),
                        text = option.label,
                        isSelected = state.category == option,
                        onClick = {
                            onChange(state.copy(category = option))
                        },
                    )
                }
            }

            SmallButton(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(174.dp, 37.dp)
                    .fillMaxWidth(),
                text = "Filter",
                onClick = onApplyFilter
            )
        }
    }
}


@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = AppTextStyles.smallTextBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        textAlign = TextAlign.Start
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun FilterSearchBottomSheetPreview() {
    FilterSearchBottomSheet(
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        state = FilterSearchState(),
        onChange = {},
        onDismiss = {},
        onApplyFilter = {}
    )
}
