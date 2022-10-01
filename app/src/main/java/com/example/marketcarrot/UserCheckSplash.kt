package com.example.marketcarrot

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

    }
}