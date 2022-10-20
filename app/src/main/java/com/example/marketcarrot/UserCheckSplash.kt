package com.example.marketcarrot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.marketcarrot.databinding.ActivityUsercheckSplashBinding

class UserCheckSplash : AppCompatActivity() {

    private lateinit var binding: ActivityUsercheckSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usercheck_splash)
        binding = ActivityUsercheckSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startCarrotBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            Toast.makeText(this,"로그인을 해주세요", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginSplash::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()

        finish()
    }

    override fun onStop() {
        super.onStop()

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        finish()
    }
}