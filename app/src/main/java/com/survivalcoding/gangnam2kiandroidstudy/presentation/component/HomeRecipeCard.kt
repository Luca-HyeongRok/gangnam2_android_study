package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import android.graphics.Color.BLUE
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun HomeRecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    isSaved: Boolean = false,
    onBookmarkClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(width = 150.dp, height = 231.dp)
    ) {
        // 회색 카드
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(176.dp)
                .background(
                    color = AppColors.gray4.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                )
                .align(Alignment.BottomCenter)
                .padding(10.dp)
        ) {
            // 제목
            Text(
                text = recipe.name,
                style = AppTextStyles.smallTextBold.copy(
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                ),
                color = AppColors.gray1,
                modifier = Modifier
                    .padding(top = 56.dp)
                    .fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            // 시간
            Column(
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    text = "Time",
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray3,
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = recipe.time,
                    style = AppTextStyles.smallerTextBold,
                    color = AppColors.gray1,
                )
            }

            // 북마크
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.BottomEnd)
                    .background(color = AppColors.white, shape = CircleShape)
                    .clickable { onBookmarkClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_bookmark_inactive),
                    contentDescription = "Bookmark",
                    modifier = Modifier.size(16.dp),
                    tint = if (isSaved) AppColors.primary80 else AppColors.gray3,
                )
            }
        }

        // 이미지에 그림자
        Box(
            modifier = Modifier
                .size(110.dp)
                .align(Alignment.TopCenter)
                .offset(y = 8.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape
                )
        )

        // 원형 음식
        AsyncImage(
            model = if (LocalInspectionMode.current) ColorDrawable(BLUE) else recipe.imageUrl,
            contentDescription = recipe.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .align(Alignment.TopCenter)
        )

        // 별점
        Box(
            modifier = Modifier
                .size(width = 45.dp, height = 23.dp)
                .align(Alignment.TopEnd)
                .offset(y = 30.dp)
                .background(
                    color = AppColors.secondary20,
                    shape = RoundedCornerShape(20.dp)
                ),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    modifier = Modifier.size(10.dp),
                    tint = AppColors.rating,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = recipe.rating.toString(),
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.black,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeRecipeCardPreview() {
    HomeRecipeCard(
        recipe = Recipe(
            id = 1,
            name = "Spicy fried rice mix chicken bali",
            chef = "Spicy Nelly",
            time = "20 min",
            category = "Chinese",
            rating = 4.0,
            imageUrl = "https://cdn.pixabay.com/photo/2019/09/07/19/02/spanish-paella-4459519_1280.jpg",
            createdAt = System.currentTimeMillis(),
        ),
        isSaved = true
    )
}
