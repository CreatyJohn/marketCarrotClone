package com.example.marketcarrot

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.createBitmap
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import com.example.marketcarrot.databinding.FragmentProfileBinding
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ProfileFragment : Fragment() {

    /** view binding for fragment */
    private val TAG = "KAKAOLOGIN"

    /** view binding for fragment */
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // fragment에서 ById 대신에 사용하는 ViewBinding을 되게 하는 핵심 코드
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        // Kakao SDK 초기화
        activity?.let { KakaoSdk.init(it, "21de26b357c402d6376672dcbda85911") }

        binding.btnKakao.text = loginStatusInfoTitle

        binding.btnKakao.setOnClickListener {
            KakaoLoginCheck(LoginSplash())
        }

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    TAG, "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                )

                binding.tvUsername.text = "${user.kakaoAccount?.profile?.nickname}"
                binding.tvUseremail.text = "${user.kakaoAccount?.email}"
                lifecycleScope.launch(Dispatchers.IO) {
                    val bitmap = convertBitmapFromURL("${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                    binding.ivProfile.setImageBitmap(bitmap)
                }
            }
        }

        return binding.root
    }

    val isLoggedIn = MutableStateFlow<Boolean>(false)

    val loginStatusInfoTitle = if (isLoggedIn.value) "카카오로 로그아웃하기" else "카카오로 로그인하기"

    fun KakaoLoginCheck(viewModel: LoginSplash) {
        val isLoggedIn = viewModel.isLoggedIn.asStateFlow()

        if (isLoggedIn.value) {
            CoroutineScope(Dispatchers.IO).launch {
                binding.btnKakao.text = loginStatusInfoTitle
                KakaoLogout()
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                binding.btnKakao.text = loginStatusInfoTitle
                KakaoLogin()
            }
        }
    }

    private fun convertBitmapFromURL(url: String): Bitmap? {

        try {
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)

            return bitmap
        } catch (e: IOException) {
            Log.e(TAG, "${e}")
        }

        return null
    }

    private suspend fun KakaoLogout(): Boolean =
        suspendCoroutine { continuation ->
            // 로그아웃
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    continuation.resume(true)
                }
                else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    continuation.resume(false)
                }
            }
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("Hello", "연결 끊기 실패", error)
                } else {
                    Log.i("Hello", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }
        }

    //     로그인 조합 예제
    private suspend fun KakaoLogin(): Boolean =
        suspendCoroutine<Boolean> { continuation ->
            // 카카오계정으로 로그인 공통 callback 구성
// 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                    continuation.resume(false) // 서스펜드 function 으로 실패 이벤트를 알려줌
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    continuation.resume(true) // 서스펜드 function 으로 성공 이벤트를 알려줌
                }
            }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireActivity())) {
                UserApiClient.instance.loginWithKakaoTalk(requireActivity()) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(requireActivity(), callback = callback)
            }
        }
}
