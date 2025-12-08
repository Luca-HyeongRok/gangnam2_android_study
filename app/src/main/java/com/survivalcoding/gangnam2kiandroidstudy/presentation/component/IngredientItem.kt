package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun IngredientItem(
    imageURL: String,
    ingredientName: String,
    ingredientWeight: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(AppColors.gray4)
            .fillMaxWidth()
            .height(76.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        // 좌측 Padding
        Spacer(modifier = Modifier.width(15.dp))

        Box(
            modifier = Modifier.size(52.dp),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = imageURL,
                contentDescription = ingredientName,
                modifier = Modifier.size(40.dp),
            )
        }

        Spacer(modifier = Modifier.width(12.dp)) // 이미지와 이름 간 간격

        // 이름
        Text(
            text = ingredientName,
            style = AppTextStyles.normalTextBold,
            modifier = Modifier.weight(1f),
        )

        // 무게
        Text(
            text = ingredientWeight,
            style = AppTextStyles.smallTextRegular.copy(color = AppColors.gray3),
            modifier = Modifier.padding(end = 15.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientItemPreview() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Box의 자식 요소를 중앙에 배치
    ) {
        IngredientItem(
            imageURL = "https://cdn.pixabay.com/photo/2017/10/06/17/17/tomato-2823826_1280.jpg",
            ingredientName = "Tomatos",
            ingredientWeight = "200g"
        )
    }
}

