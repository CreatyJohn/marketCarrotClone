package com.example.marketcarrot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.widget.Button
import com.example.marketcarrot.databinding.ActivityUsercheckSplashBinding

class UserCheckSplash : AppCompatActivity() {

    private lateinit var binding: ActivityUsercheckSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usercheck_splash)
        binding = ActivityUsercheckSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn_start = findViewById<Button>(R.id.start_carrot_btn)

        btn_start.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}