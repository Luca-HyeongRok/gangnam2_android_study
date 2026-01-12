package com.survivalcoding.gangnam2kiandroidstudy.presentation.screen.saved_recipes_legacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.survivalcoding.gangnam2kiandroidstudy.databinding.FragmentSavedRecipesLegacyBinding

/**
 * SavedRecipesLegacyFragment
 *
 * 이 Fragment는 "저장된 레시피 목록 화면"을 담당하는 레거시 UI 컴포넌트이다.
 *
 * Activity는 이 Fragment를 담는 컨테이너 역할만 수행하고,
 * 실제 화면 구성과 UI 로직은 Fragment가 책임진다.
 *
 * 또한 RecyclerView 아이템 클릭에 대한
 * "최종 처리 주체" 역할도 Fragment가 담당한다.
 */
class SavedRecipesLegacyFragment :
    Fragment(),
    SavedRecipeClickListener {   // Adapter 클릭 이벤트를 받기 위해 Interface 구현

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
     * Fragment의 View를 생성하는 단계
     *
     * - XML을 inflate
     * - ViewBinding 초기화
     *
     * 아직 View를 "사용"하는 단계는 아니다.
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
     * RecyclerView, Adapter, 리스너 등
     * "View를 실제로 조작하는 코드"는
     * 반드시 이 시점 이후에 작성해야 한다.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Adapter를 생성하면서 Fragment(this)를 전달한다.
         *
         * 이유:
         * - Adapter는 클릭을 감지
         * - Fragment는 클릭 이후 동작을 결정
         *
         * 역할과 책임을 분리하기 위한 구조
         */
        binding.recyclerView.adapter =
            SavedRecipesLegacyAdapter(listener = this)
    }

    /**
     * Adapter에서 아이템이 클릭되면 호출된다.
     *
     * 이 메서드가 "클릭 이벤트의 최종 도착 지점"이다.
     *
     * Fragment는 여기서
     * - 화면 이동
     * - Toast
     * - 로그
     * 등 어떤 행동이든 자유롭게 결정할 수 있다.
     */
    override fun onRecipeClick(recipeTitle: String) {
        // 상세 화면 이동 또는 Toast 처리
    }

    /**
     * View 생명주기 종료 시점
     *
     * Fragment 인스턴스는 남아 있을 수 있지만
     * View는 제거되므로,
     * binding을 null 처리하지 않으면 메모리 누수가 발생한다.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
