package com.example.marketcarrot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.marketcarrot.databinding.ActivityLoginSplashBinding
import com.google.firebase.firestore.auth.User
import com.kakao.sdk.common.KakaoSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginSplash : AppCompatActivity() {

    private val kakaoAuthViewModel: KakaoAuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginSplashBinding
    private val TAG = "NaverLogin"
    lateinit var userCheckSplash: UserCheckSplash

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

        KakaoLoginView(kakaoAuthViewModel)

    }

    fun KakaoLoginView(viewModel: KakaoAuthViewModel){

        val isLoggedIn = viewModel.isLoggedIn.asStateFlow()


        binding.btnKakaoLogin.setOnClickListener {

            viewModel.kakaoLogin()
            userCheckSplash.finish()

        }

    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "onDestroy",Toast.LENGTH_SHORT).show()
    }
}


