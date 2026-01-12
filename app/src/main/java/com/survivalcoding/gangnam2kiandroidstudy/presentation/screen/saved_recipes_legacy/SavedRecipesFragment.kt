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
 */
class SavedRecipesLegacyFragment : Fragment() {

    /**
     * ViewBinding 객체
     *
     * - fragment_saved_recipes_legacy.xml을 기반으로
     *   빌드 시점에 자동 생성된 Binding 클래스이다.
     *
     * - _binding / binding 두 개로 나누는 이유:
     *   Fragment의 View 생명주기는 Fragment 자체 생명주기보다 짧기 때문에
     *   View가 파괴된 이후에도 binding을 참조하면 메모리 누수가 발생할 수 있다.
     */
    private var _binding: FragmentSavedRecipesLegacyBinding? = null

    /**
     * binding은 null이 아님을 보장하고 사용하기 위한 프로퍼티
     *
     * - View가 존재하는 구간(onCreateView ~ onDestroyView)에서만 접근해야 한다.
     * - !! 연산자를 사용하지만, 생명주기를 지켜 사용하면 안전하다.
     */
    private val binding get() = _binding!!

    /**
     * Fragment의 View를 생성하는 생명주기 메서드
     *
     * - XML 레이아웃을 inflate하여 View 객체를 생성한다.
     * - ViewBinding 객체도 이 시점에서 함께 초기화된다.
     *
     * 이 단계에서는 아직 View가 Fragment에 "붙기 전" 상태이므로
     *     UI 이벤트 처리나 데이터 바인딩은 하지 않는다.
     */
    override fun onCreateView(
        inflater: LayoutInflater,        // XML → View로 변환하는 객체
        container: ViewGroup?,            // Fragment가 붙을 부모 View
        savedInstanceState: Bundle?
    ): View {
        // ViewBinding 초기화
        _binding = FragmentSavedRecipesLegacyBinding.inflate(
            inflater,
            container,
            false
        )

        // Fragment가 화면에 표시할 최상위 View 반환
        return binding.root
    }

    /**
     * View가 완전히 생성된 이후 호출되는 메서드
     *
     * - RecyclerView 설정
     * - Adapter 연결
     * - 클릭 리스너 등록
     *
     * 등의 "View를 실제로 사용하는 코드"는
     * 반드시 이 메서드 이후에 작성해야 한다.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView는 Adapter를 통해서만 데이터를 표시할 수 있다
        binding.recyclerView.adapter = SavedRecipesLegacyAdapter()
    }


    /**
     * Fragment의 View가 파괴될 때 호출되는 메서드
     *
     *  왜 binding을 null로 만드는가?
     * - Fragment 인스턴스는 백스택에 남아 있을 수 있지만
     * - View는 이미 제거된 상태일 수 있다.
     *
     * - 이때 binding이 View를 계속 참조하고 있으면
     *   메모리 누수(Leak)가 발생한다.
     *
     * 따라서 View 생명주기가 끝나는 시점에
     * binding 참조를 명시적으로 제거해야 한다.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
