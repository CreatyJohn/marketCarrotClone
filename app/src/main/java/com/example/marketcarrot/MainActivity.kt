package com.example.marketcarrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.marketcarrot.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    /** view binding */
    private lateinit var binding: ActivityMainBinding

    /** fragmentview.bottomNav */
    private val fl: FrameLayout by lazy {
        findViewById(R.id.fl_)
    }

    private val bn: BottomNavigationView by lazy {
        findViewById(R.id.bn_)
    }

    /** view binding setting in onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {
        //binding super directory(root)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** bottomNavigation setting in onCreate */
        //fragment section
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(fl.id, HomeFragment()).commit()

        bn.setOnItemReselectedListener {
            replaceFragment(
                when (it.itemId) {
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_chat -> ChatFragment()
                    R.id.menu_location -> LocationFragment()
                    R.id.menu_profile -> ProfileFragment()
                    else -> NeighborFragment()
                }
            )
        }

        Toast.makeText(this, "로그인이 필요합니다.",Toast.LENGTH_SHORT).show()

        SplashScreen()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl.id, fragment).commit()
    }

    /**앱 시작마다 필요한 동네 GPS 설정*/
    override fun onStart() {
        super.onStart()

        startService(GpsTrackerService.getIntent(this@MainActivity))

        Toast.makeText(this, "onStart",Toast.LENGTH_SHORT).show()
    }

    /**어플의 기능을 정의하는 곳.. ex) 유저의 정보, 게시물을 정의할 예정 */
    override fun onResume() {
        super.onResume()

        Toast.makeText(this, "onResume",Toast.LENGTH_SHORT).show()
    }

    /** Remove regist.User Data, Stop GPSTrackerService Data */
    override fun onStop() {
        super.onStop()

        GpsTrackerService.stopTracking(this@MainActivity)

    }
}

