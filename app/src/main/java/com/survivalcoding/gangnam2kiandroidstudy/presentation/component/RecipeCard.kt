package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun RecipeCard(
    title: String,
    userName: String,
    cookingTime: String,
    starReview: String,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = Modifier.size(16.dp))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(165.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        // 배경 이미지
        Image(
            painter = painterResource(R.drawable.food),
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 내용
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            // 오른쪽 위(별점)
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .height(16.dp)
                    .background(
                        AppColors.secondary20,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(horizontal = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "StarReview",
                    tint = AppColors.rating,
                    modifier = Modifier.size(8.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = starReview,
                    style = AppTextStyles.smallTextRegular.copy(
                        color = AppColors.black,
                        fontSize = 8.sp
                    )
                )
            }

            // 아래 왼쪽: 제목 / 유저 정보
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(0.5f)
            ) {
                Text(
                    text = title,
                    style = AppTextStyles.normalTextBold.copy(
                        color = AppColors.white,
                        fontSize = 14.sp
                    ),
                    maxLines = 2
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "By $userName",
                    style = AppTextStyles.smallTextRegular.copy(
                        color = AppColors.white.copy(alpha = 0.9f),
                        fontSize = 8.sp
                    )
                )
            }

            // 아래 오른쪽: 시간 + 저장
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_timer),
                    contentDescription = "Cooking Time",
                    tint = AppColors.gray4,
                    modifier = Modifier
                        .size(17.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "$cookingTime min",
                    style = AppTextStyles.smallTextRegular.copy(
                        color = AppColors.white,
                        fontSize = 11.sp
                    )
                )
                Spacer(Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(AppColors.white),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_bookmark_inactive),
                        contentDescription = "bookmark Recipe",
                        tint = AppColors.primary80,
                        modifier = Modifier
                            .size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RecipeCard(
            title = "Traditional spare ribs baked",
            userName = "Chef John",
            cookingTime = "20",
            starReview = "4.0",
        )
    }
}
