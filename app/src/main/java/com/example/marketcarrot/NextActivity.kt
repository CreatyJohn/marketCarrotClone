package com.example.marketcarrot

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marketcarrot.databinding.ActivityNextBinding

class NextActivity : AppCompatActivity() {

    /** view binding */
    private lateinit var binding: ActivityNextBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE)
        val id = sharedPreferences.getInt("NUM", -1).toString()
        val pass = sharedPreferences.getString("NAME", "ERROR")

        binding.activityNextTvId.text = id
        binding.activityNextTvPass.text = pass
    }
}