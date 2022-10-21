package com.example.marketcarrot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.marketcarrot.databinding.FragmentProfileBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.flow.asStateFlow

class ProfileFragment : Fragment() {

    /** view binding for fragment */

    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()
    private val TAG = "NaverLogin"

    /** view binding for fragment */
    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // fragment에서 ById 대신에 사용하는 ViewBinding을 되게 하는 핵심 코드
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Kakao SDK 초기화
        activity?.let { KakaoSdk.init(it, "21de26b357c402d6376672dcbda85911") }

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(KakaoAuthViewModel.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(
                    KakaoAuthViewModel.TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                binding.tvUsername.text = "${user.kakaoAccount?.profile?.nickname}"
                binding.tvUseremail.text = "${user.kakaoAccount?.email}"
            }
        }

        binding.btnKakaoLogout.setOnClickListener {
            KakaoLoginCheck(kakaoAuthViewModel)
        }

        return binding.root
    }

    fun KakaoLoginCheck(viewModel: KakaoAuthViewModel){
        val isLoggedIn = viewModel.isLoggedIn.asStateFlow()

        val loginStatusInfoTitle = if (isLoggedIn.value) "카카오로 로그아웃하기" else "카카오로 로그인하기"

        binding.btnKakaoLogout.text = loginStatusInfoTitle
        if (isLoggedIn.value) viewModel.kakaoLogout() else viewModel.kakaoLogin()
    }
}