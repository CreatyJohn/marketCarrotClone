package com.example.marketcarrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.marketcarrot.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

//data class UserCheckStatus(val position: Int, var isChecked: Boolean)

class MainActivity : AppCompatActivity() {

    /** view binding */
    private lateinit var binding: ActivityMainBinding

    /** fragmentview.bottomNav */
    private val fl_main: FrameLayout by lazy {
        findViewById(R.id.fl_main)
    }

    private val bn_main: BottomNavigationView by lazy {
        findViewById(R.id.bn_main)
    }

    /** Fragment에 RecyclerView를 적용시키기 위한 함수 */
    private fun replaceFragment(homeFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_main,homeFragment)
        fragmentTransaction.commit()
    }

    /** view binding setting in onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {

        /** binding super directory(root) */
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        supportFragmentManager.beginTransaction().add(fl_main.id, Fragment()).commit()

        BottomNavigationView.OnNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    HomeFragment()
                    true
                }
                R.id.menu_chat -> {
                    ChatFragment()
                    true
                }
                R.id.menu_location -> {
                    LocationFragment()
                    true
                }
                R.id.menu_profile -> {
                    ProfileFragment()
                    true
                }
                R.id.menu_neighbor -> {
                    NeighborFragment()
                    true
                }
                else -> false
            }
        }

        bn_main.setOnNavigationItemSelectedListener{
            replaceFragment(
                when (it.itemId) {
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_chat -> ChatFragment()
                    R.id.menu_location -> LocationFragment()
                    R.id.menu_profile -> ProfileFragment()
                    else -> NeighborFragment()
                }
            )
            true
        }
    }

    /**앱 시작마다 필요한 동네 GPS 설정*/
    override fun onStart() {
        super.onStart()

        startService(GpsTrackerService.getIntent(this@MainActivity))

        Toast.makeText(this, "시작합니다",Toast.LENGTH_SHORT).show()
    }

    /**어플의 기능을 정의하는 곳.. ex) 유저의 정보, 게시물을 정의할 예정 */
    override fun onResume() {
        super.onResume()

        Toast.makeText(this, "onResume",Toast.LENGTH_SHORT).show()
    }

    /** Remove regist,User Data, Stop GPSTrackerService Data */
    override fun onStop() {
        super.onStop()

        GpsTrackerService.stopTracking(this@MainActivity)

        Toast.makeText(this, "onStop",Toast.LENGTH_SHORT).show()

    }

    /** Remove regist,User Data */
    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "onDestroy",Toast.LENGTH_SHORT).show()
    }
}

