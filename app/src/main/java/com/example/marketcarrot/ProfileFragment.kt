package com.example.marketcarrot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import coil.api.load
import com.example.marketcarrot.databinding.FragmentProfileBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.bumptech.glide.Glide
import com.kakao.sdk.friend.R.id.container
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    /** view binding for fragment */
    private val TAG = "KAKAOLOGIN"

    /** view binding for fragment */
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var profileFragment: ProfileFragment
    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // fragment에서 ById 대신에 사용하는 ViewBinding을 되게 하는 핵심 코드
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        getActivity()?.let { Glide.with(it) }

        // Kakao SDK 초기화
        activity?.let { KakaoSdk.init(it, "21de26b357c402d6376672dcbda85911") }

//        val isLoggedIn = MutableStateFlow<Boolean>(false)
//        val loginStatusInfoTitle = if (isLoggedIn.value) "카카오로 로그인하기" else "카카오로 로그아웃하기"

        KakaoLoginCheck(LoginSplash())

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    TAG, "사용자 정보 요청 성" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )

                binding.tvUsername.text = "${user.kakaoAccount?.profile?.nickname}"
                binding.tvUseremail.text = "${user.kakaoAccount?.email}"
                binding.ivProfile.load("${user.kakaoAccount?.profile?.thumbnailImageUrl}")
            }
        }

        return binding.root
    }

    fun KakaoLoginCheck(viewModel: LoginSplash) {
        val isLoggedIn = viewModel.isLoggedIn.asStateFlow()

        binding.btnKakaoOut.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                KakaoLogout()
                activity?.let {
                    val intent = Intent(it, UserCheckSplash::class.java)
                    it.startActivity(intent)
                }
                activity?.supportFragmentManager
                    ?.beginTransaction()
                    ?.remove(this@ProfileFragment)
                    ?.commit()
            }
        }

    }

//    /**뷰의 숨김,표시,중단을 담당하는 funtion */
//    fun View.hide() {
//        this.visibility=GONE
//    }
//    fun View.show() {
//        this.visibility=VISIBLE
//    }
//    fun View.makeInvisible() {
//        this.visibility=INVISIBLE
//    }

    // Fragment 새로고침
//    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
//        var ft: FragmentTransaction = fragmentManager.beginTransaction()
//        ft.detach(fragment).attach(fragment).commit()
//    }

    fun refreshFragment(context: Context?) {
        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment = fragmentManager.findFragmentById(R.id.cl_profile)
                currentFragment?.let {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }
    }

    private suspend fun KakaoLogout(): Boolean =
        // 연결 끊기
        suspendCoroutine { continuation ->
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("Hello", "연결 끊기 실패", error)
                } else {
                    Log.i("Hello", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }

            // 로그아웃
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    continuation.resume(true)
                } else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    continuation.resume(false)
                }
            }
        }
}
