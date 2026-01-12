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
 *
 * Adapter는
 *   - View 생성 책임
 *   - 데이터 ↔ View 연결 책임
 * 만 가지며,
 * 화면 이동이나 비즈니스 로직은 담당하지 않는다.
 */
class SavedRecipesLegacyAdapter(
    private val listener: SavedRecipeClickListener
) : RecyclerView.Adapter<SavedRecipesLegacyAdapter.SavedRecipeViewHolder>() {

    /**
     * 더미 데이터
     * 나중에 ViewModel 또는 Repository에서 전달받는다.
     */
    private val items = listOf(
        "김치볶음밥",
        "된장찌개",
        "불고기"
    )

    /**
     * ViewHolder
     *
     * - RecyclerView 아이템 하나당 하나의 ViewHolder가 대응된다.
     * - findViewById / ViewBinding을 이 시점에서 "단 한 번만" 수행한다.
     *
     * ViewHolder를 사용하는 이유
     *   → 스크롤할 때마다 View 탐색을 반복하지 않기 위함
     *   → 성능 최적화의 핵심 개념
     */
    class SavedRecipeViewHolder(
        val binding: ItemSavedRecipeLegacyBinding
    ) : RecyclerView.ViewHolder(binding.root)

    /**
     * ViewHolder를 "생성"하는 단계
     *
     * 언제 호출되나?
     * - 화면에 표시할 ViewHolder가 부족할 때만 호출된다.
     *
     * 역할:
     * - XML 레이아웃을 inflate
     * - ViewHolder 객체 생성
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
     * - 이미 생성된 ViewHolder를 재사용하면서
     * - position에 해당하는 데이터만 교체한다.
     *
     * RecyclerView가 빠른 이유:
     *   → View를 새로 만들지 않고 재사용하기 때문
     */
    override fun onBindViewHolder(
        holder: SavedRecipeViewHolder,
        position: Int
    ) {
        val title = items[position]
        holder.binding.textTitle.text = title

        /**
         * 클릭 이벤트 처리 방식
         *
         * - Adapter는 클릭을 "감지"만 한다.
         * - 클릭 이후의 행동은 Fragment로 위임한다.
         *
         * Adapter가 화면 이동을 직접 처리하면
         *    구조가 강하게 결합되어 재사용이 불가능해진다.
         */
        holder.itemView.setOnClickListener {
            listener.onRecipeClick(title)
        }
    }

    /**
     * RecyclerView가 표시해야 할 전체 아이템 개수
     *
     * RecyclerView는 이 값만큼
     * ViewHolder를 필요에 따라 생성/재사용한다.
     */
    override fun getItemCount(): Int = items.size
}
