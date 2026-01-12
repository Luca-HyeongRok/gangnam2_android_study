package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.survivalcoding.gangnam2kiandroidstudy.databinding.ItemSavedRecipeLegacyBinding

/**
 * SavedRecipesLegacyAdapter
 *
 * RecyclerView는 스크롤 시 모든 아이템을 새로 만들지 않는다.
 * 필요한 ViewHolder만 생성하고, 화면에서 사라진 ViewHolder를 재사용한다.
 *
 * Adapter는 이 "중간 관리자" 역할을 수행한다.
 */
class SavedRecipesLegacyAdapter :
    RecyclerView.Adapter<SavedRecipesLegacyAdapter.SavedRecipeViewHolder>() {

    /**
     * 더미 데이터
     */
    private val items = listOf(
        "김치볶음밥",
        "된장찌개",
        "불고기"
    )

    /**
     * ViewHolder
     *
     * - 하나의 아이템 View를 보관하는 객체
     * - findViewById / ViewBinding을 여기서 1번만 수행한다
     */
    class SavedRecipeViewHolder(
        val binding: ItemSavedRecipeLegacyBinding
    ) : RecyclerView.ViewHolder(binding.root)

    /**
     * ViewHolder를 "생성"하는 단계
     *
     * - 스크롤 중 필요한 경우에만 호출됨
     * - View를 새로 만드는 비용이 크기 때문에
     *   최대한 적게 호출되도록 RecyclerView가 관리한다
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedRecipeViewHolder {
        val binding = ItemSavedRecipeLegacyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SavedRecipeViewHolder(binding)
    }

    /**
     * ViewHolder에 "데이터를 바인딩"하는 단계
     *
     * - 이미 만들어진 ViewHolder를 재사용하면서
     *   position에 맞는 데이터만 교체한다
     */
    override fun onBindViewHolder(
        holder: SavedRecipeViewHolder,
        position: Int
    ) {
        holder.binding.textTitle.text = items[position]
    }

    /**
     * RecyclerView가 표시해야 할 전체 아이템 개수
     */
    override fun getItemCount(): Int = items.size
}
