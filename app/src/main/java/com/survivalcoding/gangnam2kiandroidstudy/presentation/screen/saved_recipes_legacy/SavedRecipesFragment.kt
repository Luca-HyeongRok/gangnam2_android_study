package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.survivalcoding.gangnam2kiandroidstudy.databinding.FragmentSavedRecipesLegacyBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * SavedRecipesLegacyFragment
 *
 * 이 Fragment는 "저장된 레시피 목록 화면"을 담당하는 레거시 UI 컴포넌트이다.
 *
 * Activity나 Compose는 이 Fragment를 "호스팅"만 하며,
 * 실제 화면 구성과 RecyclerView 제어는 Fragment가 책임진다.
 *
 *
 * - Fragment는 Activity에 의존하지 않는다.
 * - 클릭 이벤트는 SavedRecipesLegacyCallback을 통해 외부로 전달된다.
 */
class SavedRecipesLegacyFragment :
    Fragment(),
    SavedRecipeClickListener {   // Adapter 클릭 이벤트를 받기 위해 구현

    /**
     * ViewBinding 객체
     *
     * Fragment의 View 생명주기는 Fragment 자체 생명주기보다 짧기 때문에
     * View가 파괴된 이후에도 binding을 유지하면 메모리 누수가 발생한다.
     *
     * → _binding / binding 패턴을 사용하는 이유
     */
    private var _binding: FragmentSavedRecipesLegacyBinding? = null
    private val binding get() = _binding!!

    /**
     * 외부(호스트)로 클릭 이벤트를 전달하기 위한 콜백
     *
     * - Activity
     * - Compose(AndroidView)
     * - 테스트 코드
     *
     * 어떤 환경이든 이 콜백을 구현해서 주입할 수 있다.
     */
    private var callback: SavedRecipesCallback? = null
    private val viewModel: SavedRecipesLegacyViewModel by viewModel()

    /**
     * Fragment 생성 이후 외부에서 콜백 주입
     *
     * Fragment는 생성자에서 콜백을 받지 않는 것이 원칙이므로
     * setter 방식으로 주입한다.
     */
    fun setCallback(callback: SavedRecipesCallback?) {
        this.callback = callback
    }

    /**
     * Fragment의 View를 생성하는 단계
     *
     * - XML inflate
     * - ViewBinding 초기화
     *
     * 아직 RecyclerView를 직접 다루는 단계는 아니다.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedRecipesLegacyBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    /**
     * View가 완전히 생성된 이후 호출되는 메서드
     *
     * RecyclerView, Adapter, LayoutManager 등
     * "View를 실제로 조작하는 코드"는
     * 반드시 이 시점 이후에 작성해야 한다.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * RecyclerView는 반드시 LayoutManager가 필요하다.
         *
         * 이 설정이 없으면:
         * - 아이템이 보이지 않거나
         * - 클릭 이벤트가 정상 동작하지 않을 수 있다.
         */
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        /**
         * Adapter 생성
         *
         * listener = this
         * → 클릭 이벤트를 Fragment가 직접 수신
         */
        val adapter = SavedRecipesLegacyAdapter(listener = this)
        binding.recyclerView.adapter = adapter

        /**
         * 레거시 구조에서 화면 갱신 시점과 책임을 분리하기 위해
         * ViewModel 상태를 수집하고, Adapter에 전달한다.
         */
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    // RecyclerView용 단순 모델로 변환해 레거시 구조를 분리한다.
                    val items = state.recipes.map { recipe ->
                        SavedRecipesLegacyItem(
                            id = recipe.id,
                            title = recipe.name,
                            // 레거시 모델로 매핑하면서 이미지 URL까지 함께 전달한다.
                            imageUrl = recipe.imageUrl
                        )
                    }
                    // 북마크 변경 시 목록이 즉시 반영되도록 최신 리스트를 전달한다.
                    adapter.submitList(items)
                }
            }
        }
    }

    /**
     * Adapter에서 아이템이 클릭되면 호출된다.
     *
     * 이 메서드는
     * - Fragment 내부의 최종 클릭 수신 지점이며
     * - 실제 처리는 외부 콜백으로 위임한다.
     */
    override fun onRecipeClick(recipeId: Int, recipeTitle: String) {
        android.util.Log.d(
            "LegacyCheck",
            "현재 Fragment 호스트 = ${requireActivity()::class.java.simpleName}"
        )

        callback?.onRecipeClick(recipeId, recipeTitle)
    }

    /**
     * View 생명주기 종료 시점
     *
     * - ViewBinding 해제
     * - 콜백 참조 제거
     *
     * Fragment는 남아 있을 수 있으므로
     * 반드시 View 관련 참조를 정리해야 한다.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        callback = null
        _binding = null
    }
}
