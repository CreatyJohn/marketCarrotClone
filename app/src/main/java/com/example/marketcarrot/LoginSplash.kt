package com.example.marketcarrot

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marketcarrot.databinding.ActivityLoginSplashBinding

class LoginSplash : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSplashBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_splash)
        binding = ActivityLoginSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** SharedPreferences Code */
        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE)

        binding.activityMainBtn.setOnClickListener {
            val num = binding.activityMainEtId.text.toString().toInt()
            val pass = binding.activityMainEtPass.text.toString()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("ID", num)
            editor.putString("PASS", pass)
            editor.apply()

            startActivity(Intent(this, NextActivity::class.java))
        }
    }
}