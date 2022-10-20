package com.example.marketcarrot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.marketcarrot.databinding.ActivityLoginSplashBinding
import com.kakao.sdk.common.KakaoSdk
import kotlinx.android.synthetic.main.activity_login_splash.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectIndexed
import org.w3c.dom.Text

class LoginSplash : AppCompatActivity() {

    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginSplashBinding
    private val TAG = "NaverLogin"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_splash)
        binding = ActivityLoginSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        NaverIdLoginSDK.initialize(this, "o3UsmTVEYGORAREFn9w_", "o3UsmTVEYGORAREFn9w_", "ddangeun_clone")
//        NaverIdLoginSDK.authenticate(this, oauthLoginCallback)

        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, "21de26b357c402d6376672dcbda85911")

        binding.btnGoback.setOnClickListener {

            finish()
            startActivity(Intent(this, UserCheckSplash::class.java))
        }

        KakaoLoginView(kakaoAuthViewModel)
    }

    fun KakaoLoginView(viewModel: KakaoAuthViewModel){

        val isLoggedIn = viewModel.isLoggedIn.asStateFlow()

        val loginStatusInfoTitle = if (isLoggedIn.value) "로그인 상태" else "로그아웃 상태"

        var check: Boolean = true

        binding.btnKakaoLogin.setOnClickListener {
            when (check){
                true -> {
                    viewModel.kakaoLogin()
                    btn_kakao_login.text = "카카오 로그아웃하기"
                    check = false
                }
                false -> {
                    viewModel.kakaoLogout()
                    btn_kakao_login.text = "카카오로 로그인하기"
                    check = true
                }
            }
        }
    }
}


