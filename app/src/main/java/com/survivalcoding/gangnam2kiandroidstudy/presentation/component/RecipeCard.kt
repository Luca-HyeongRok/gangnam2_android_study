package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import android.graphics.Color.YELLOW
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.domain.model.recipe.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun RecipeCard(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(315 / 150f)
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            model = if (LocalInspectionMode.current) {
                ColorDrawable(YELLOW)
            } else {
                recipe.imageUrl
            },
            contentDescription = recipe.name,
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 1f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(37.dp)
                .height(16.dp)
                .offset(x = -10.dp, y = 10.dp)
                .background(
                    color = AppColors.secondary20,
                    shape = RoundedCornerShape(size = 20.dp)
                )
                .padding(horizontal = 7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star",
                tint = AppColors.rating,
                modifier = Modifier.size(8.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = recipe.rating.toString(),
                style = AppTextStyles.smallerTextRegular.copy(fontSize = 8.sp),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                modifier = Modifier.width(190.dp)
            ) {
                Text(
                    text = recipe.name,
                    style = AppTextStyles.smallTextBold.copy(
                        color = AppColors.white
                    ),
                    maxLines = 2
                )
                Text(
                    text = "By ${recipe.chef}",
                    style = AppTextStyles.smallerTextRegular.copy(
                        fontSize = 8.sp,
                        color = AppColors.gray4
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .width(94.dp)
                    .aspectRatio(94 / 24f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_timer),
                    contentDescription = "Cooking Time",
                    tint = AppColors.gray4,
                    modifier = Modifier.size(17.dp)
                )

                Text(
                    text = recipe.time, // "20 min" 형태 그대로 사용
                    style = AppTextStyles.smallerTextRegular.copy(
                        color = AppColors.white
                    ),
                    maxLines = 1,
                    modifier = Modifier.padding(start = 5.dp, end = 10.dp)
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(AppColors.primary20)
                        .clickable { onBookmarkClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_bookmark_inactive),
                        contentDescription = "bookmark Recipe",
                        tint = AppColors.primary80,
                        modifier = Modifier.size(16.dp)
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RecipeCard(
            recipe = Recipe(
                id = 1,
                category = "Chinese",
                name = "Traditional spare ribs baked",
                imageUrl = "https://cdn.pixabay.com/photo/2017/11/10/15/04/steak-2936531_1280.jpg",
                chef = "Chef John",
                time = "20 min",
                rating = 4.0,
                createdAt = System.currentTimeMillis()
            ), onBookmarkClick = {}
        )
    }
}
