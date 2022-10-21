package com.example.marketcarrot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.marketcarrot.databinding.ActivityLoginSplashBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LoginSplash : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSplashBinding
    private val TAG = "KAKAOLOGIN"

    override fun onCreate(savedInstanceState: Bundle?) {

        // 다른 초기화 코드들
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_splash)
        binding = ActivityLoginSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Kakao SDK 초기화
        KakaoSdk.init(this, "21de26b357c402d6376672dcbda85911")

        binding.btnGoback.setOnClickListener {
            finish()
            startActivity(Intent(this, UserCheckSplash::class.java))
        }

        binding.btnKakaoLogin.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                KakaoLogin()
            }
            if (isLoggedIn.value == true) {
                finish()
            } else {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "onDestroy",Toast.LENGTH_SHORT).show()
    }

    val isLoggedIn = MutableStateFlow<Boolean>(false)

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
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginSplash)) {
                UserApiClient.instance.loginWithKakaoTalk(this@LoginSplash) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this@LoginSplash, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this@LoginSplash, callback = callback)
            }
        }
}


