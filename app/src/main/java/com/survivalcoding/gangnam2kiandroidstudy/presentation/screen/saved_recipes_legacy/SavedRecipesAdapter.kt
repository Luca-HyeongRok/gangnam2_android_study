package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.survivalcoding.gangnam2kiandroidstudy.R
import com.survivalcoding.gangnam2kiandroidstudy.databinding.ItemSavedRecipeLegacyBinding

/**
 * SavedRecipesLegacyAdapter
 *
 * DiffUtil + ListAdapter를 사용하는 레거시 RecyclerView Adapter
 *
 * ListAdapter를 사용하는 이유:
 * - submitList() 호출 시
 *   → 이전 리스트와 새 리스트를 DiffUtil로 비교
 *   → 변경된 아이템만 RecyclerView에 반영
 *
 * 즉, notifyDataSetChanged()를 직접 호출하지 않는다.
 */
class SavedRecipesLegacyAdapter(
    private val listener: SavedRecipeClickListener
) : ListAdapter<SavedRecipesLegacyItem, SavedRecipesLegacyAdapter.SavedRecipeViewHolder>(
    DiffCallback
) {

    /**
     * ViewHolder
     *
     * - RecyclerView 아이템 View 하나를 보관한다.
     * - ViewBinding을 통해 View 탐색 비용을 제거한다.
     */
    class SavedRecipeViewHolder(
        val binding: ItemSavedRecipeLegacyBinding
    ) : RecyclerView.ViewHolder(binding.root)

    /**
     * ViewHolder 생성
     *
     * - 화면에 필요한 ViewHolder가 부족할 때만 호출된다.
     * - XML inflate 비용은 크기 때문에
     *   RecyclerView가 재사용을 통해 호출 횟수를 최소화한다.
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
     * ViewHolder에 데이터 바인딩
     *
     * - 이미 생성된 ViewHolder를 재사용
     * - position에 해당하는 데이터만 교체
     */
    override fun onBindViewHolder(
        holder: SavedRecipeViewHolder,
        position: Int
    ) {
        val item = getItem(position)

        holder.binding.textTitle.text = item.title

        /**
         * 레거시 RecyclerView에서 이미지 로딩
         *
         * - Coil 3 사용
         * - Recipe.imageUrl을 그대로 로드
         * - placeholder / error 는 기존 drawable 재사용
         */
        holder.binding.imageThumbnail.setImageResource(R.drawable.food)
        holder.binding.imageThumbnail.load(item.imageUrl) {
            listener(
                onStart = {
                    holder.binding.imageThumbnail.setImageResource(R.drawable.food)
                },
                onError = { _, _ ->
                    holder.binding.imageThumbnail.setImageResource(R.drawable.food)
                }
            )
        }

        holder.itemView.setOnClickListener {
            listener.onRecipeClick(item.id, item.title)
        }
    }

    /**
     * DiffUtil 콜백
     *
     * RecyclerView는 이 로직을 통해
     * "어떤 아이템이 변경되었는지" 판단한다.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<SavedRecipesLegacyItem>() {

        /**
         * 두 아이템이 같은 아이템인지 비교
         *
         * - 고유 ID 기준 비교
         */
        override fun areItemsTheSame(
            oldItem: SavedRecipesLegacyItem,
            newItem: SavedRecipesLegacyItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        /**
         * 아이템 내용이 같은지 비교
         *
         * - UI를 다시 그릴 필요가 있는지 판단
         */
        override fun areContentsTheSame(
            oldItem: SavedRecipesLegacyItem,
            newItem: SavedRecipesLegacyItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
