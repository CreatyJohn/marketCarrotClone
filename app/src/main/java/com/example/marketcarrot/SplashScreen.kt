package com.example.marketcarrot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        /** Do Fullscreen for splash screen */
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        /** Hold on for delay : splash screen */
        Handler().postDelayed({
            val intent = Intent(this, UserCheckSplash::class.java)
            startActivity(intent)
            finish()
        },1000) // 1초 딜레이

    }
}