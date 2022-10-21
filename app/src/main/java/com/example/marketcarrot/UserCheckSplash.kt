package com.example.marketcarrot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marketcarrot.databinding.ActivityUsercheckSplashBinding

class UserCheckSplash : AppCompatActivity() {

    private lateinit var binding: ActivityUsercheckSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usercheck_splash)
        binding = ActivityUsercheckSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startCarrotBtn.setOnClickListener {
            startActivity(Intent(this, LoginSplash::class.java))
            finish()
        }

//        binding.btnLogin.setOnClickListener {
//            Toast.makeText(this,"로그인을 해주세요", Toast.LENGTH_SHORT).show()
//
//        }
    }
}