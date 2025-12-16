package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import android.graphics.Color.YELLOW
import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.survivalcoding.gangnam2kiandroidstudy.model.recipe.Recipe
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles


@Composable
fun SearchRecipeCard(
    modifier: Modifier = Modifier,
    recipe: Recipe,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop,
            model = if (LocalInspectionMode.current) {
                ColorDrawable(YELLOW)
            } else {
                recipe.imageUrl
            },
            contentDescription = recipe.name
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
                modifier = Modifier.size(12.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Text(
                    text = recipe.name,
                    style = AppTextStyles.smallerTextBold.copy(
                        color = AppColors.white,
                        fontSize = 11.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "By ${recipe.chef}",
                    style = AppTextStyles.smallerTextRegular.copy(
                        fontSize = 8.sp,
                        color = AppColors.gray3
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchRecipeCardPreview() {
    SearchRecipeCard(
        recipe = Recipe(
            id = 1,
            name = "Traditional spare ribs baked",
            chef = "Chef John",
            time = "20 min",
            category = "Chinese",
            rating = 4.0,
            imageUrl = "image",
            createdAt = System.currentTimeMillis()
        )
    )

}