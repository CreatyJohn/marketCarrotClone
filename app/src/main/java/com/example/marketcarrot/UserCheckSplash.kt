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

        val btn_start = findViewById<Button>(R.id.start_carrot_btn)
        val btn_login = findViewById<Button>(R.id.uc_login_btn)

        btn_start.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btn_login.setOnClickListener {
            Toast.makeText(this,"로그인을 해주세요", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginSplash::class.java))
            finish()
        }

    }
}