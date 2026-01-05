package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.IngredientItem
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.ProcedureItem
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.RecipeDropdownMenu
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.Tabs2
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.button.SmallButton
import com.survivalcoding.gangnam2kiandroidstudy.presentation.component.card.RecipeCard
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun RecipeDetailScreen(
    state: RecipeDetailState,
    modifier: Modifier = Modifier,
    onAction: (RecipeDetailAction) -> Unit,
    onBackClick: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(AppColors.white)
            ) {

                /* ===== Top Space ===== */
                Spacer(modifier = Modifier.height(44.dp))

                /* ===== Top Bar ===== */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onBackClick() }
                    )

                    Box {
                        Icon(
                            imageVector = Icons.Default.MoreHoriz,
                            contentDescription = "more",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    onAction(RecipeDetailAction.OnDropDownClick)
                                }
                        )

                        RecipeDropdownMenu(
                            expanded = state.isDropDownExpanded,
                            onDismissRequest = { onAction(RecipeDetailAction.OnDropDownDismiss) },
                            onShareClick = { onAction(RecipeDetailAction.OnShareClick) },
                        )
                    }
                }

                /* ===== Recipe Card ===== */
                state.recipe?.let {
                    RecipeCard(
                        recipe = it,
                        modifier = Modifier.padding(horizontal = 30.dp),
                        onBookmarkClick = {},
                        onCardClick = {}
                    )
                }

                /* ===== Title & Review ===== */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = state.recipe?.name ?: "",
                        style = AppTextStyles.smallTextBold,
                        modifier = Modifier.width(194.dp)
                    )

                    Text(
                        text = "(13k Reviews)",
                        style = AppTextStyles.smallerTextRegular.copy(
                            fontSize = 14.sp,
                            color = AppColors.gray3
                        )
                    )
                }

                /* ===== Chef Info ===== */
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.chef_profile),
                            contentDescription = "profile image",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = state.recipe?.chef ?: "",
                                style = AppTextStyles.smallTextBold
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_location),
                                    contentDescription = "location",
                                    tint = AppColors.primary80,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = state.recipe?.address ?: "",
                                    style = AppTextStyles.smallerTextRegular.copy(
                                        color = AppColors.gray3
                                    )
                                )
                            }
                        }
                    }

                    SmallButton(
                        text = "Follow",
                        modifier = Modifier.width(85.dp),
                        onClick = {}
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                /* ===== Tabs ===== */
                Tabs2(
                    labels = listOf("Ingredient", "Procedure"),
                    selectedIndex = state.selectedIndex,
                    onValueChange = {
                        onAction(RecipeDetailAction.OnValueChange(it))
                    }
                )

                Spacer(modifier = Modifier.height(22.dp))

                /* ===== Tab Content ===== */
                if (state.selectedIndex == 0) {

                    // Ingredient Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_serve),
                                contentDescription = "serve",
                                tint = AppColors.gray3
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "1 serve",
                                style = AppTextStyles.smallerTextRegular.copy(
                                    color = AppColors.gray3
                                )
                            )
                        }

                        Text(
                            text = "${state.ingredients.size} Items",
                            style = AppTextStyles.smallerTextRegular.copy(
                                color = AppColors.gray3
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.ingredients) { ingredient ->
                            IngredientItem(
                                imageURL = ingredient.image,
                                ingredientName = ingredient.name,
                                ingredientWeight = "${ingredient.amount}g"
                            )
                        }
                    }

                } else {

                    // Procedure Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_serve),
                                contentDescription = "serve",
                                tint = AppColors.gray3
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "1 serve",
                                style = AppTextStyles.smallerTextRegular.copy(
                                    color = AppColors.gray3
                                )
                            )
                        }

                        Text(
                            text = "${state.procedures.size} Steps",
                            style = AppTextStyles.smallerTextRegular.copy(
                                color = AppColors.gray3
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.procedures) {
                            ProcedureItem(procedure = it)
                        }
                    }
                }
            }

            if (state.isShareDialogVisible) {
                // 오버레이
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable(
                            onClick = { onAction(RecipeDetailAction.OnShareDialogDismiss) },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                )

                // 다이얼로그
                Box(
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    RecipeLinkDialog(
                        recipeLink = state.shareLink,
                        onCopyLinkClick = {
                            onAction(RecipeDetailAction.OnCopyLinkClick(state.shareLink))
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = state.message) {
        state.message?.let {
            snackbarHostState.showSnackbar(it)
            onAction(RecipeDetailAction.OnMessageShown)
        }
    }
}

@Composable
private fun RecipeLinkDialog(
    recipeLink: String,
    onCopyLinkClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.white)
            .padding(20.dp)
            .clickable(
                onClick = {},
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Recipe Link",
            style = AppTextStyles.mediumTextBold
        )

        Text(
            text = "Copy recipe link and share your recipe link with friends and family.",
            style = AppTextStyles.smallerTextRegular
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(AppColors.gray4)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = recipeLink,
                style = AppTextStyles.smallerTextRegular,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Copy Link",
                style = AppTextStyles.smallerTextRegular.copy(color = AppColors.primary100),
                modifier = Modifier
                    .clickable(
                        onClick = onCopyLinkClick,
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
                    .padding(start = 8.dp)
            )
        }
    }
}
