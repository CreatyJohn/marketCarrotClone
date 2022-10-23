package com.example.marketcarrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.marketcarrot.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_location.*
import net.daum.android.map.MapView

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

    private fun replaceFragment(homeFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_main,homeFragment)
        fragmentTransaction.commit()
    }

    val datas: ArrayList<Data> = arrayListOf(
        Data("첫번째 제품", "상일동", "20,000", R.drawable.im_1),
        Data("두번째 제품", "중랑구 목동", "46,000", R.drawable.im_2),
        Data("세번째 제품", "하남시 미사1동", "82,000", R.drawable.im_3),
        Data("네번째 제품", "가동","25,000", R.drawable.im_4),
        Data("다섯번째 제품",  "나동","46,000", R.drawable.im_5),
        Data("여섯번째 제품", "사동","9,300", R.drawable.im_6),
        Data("일곱번째 제품", "다동","256,000", R.drawable.im_7),
        Data("여덞번째 제품", "마동","134,000", R.drawable.im_8),
        Data("아홉번째 제품", "상일동","53,000", R.drawable.im_9),
        Data("열번째 제품", "명일동","68,000", R.drawable.im_10),
        Data("열한번째 제품", "강동구","60,000", R.drawable.im_11),
        Data("열두번째 제품", "중동","83,000", R.drawable.im_12),
    )

    /** view binding setting in onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {

        Toast.makeText(this, "onCreate",Toast.LENGTH_SHORT).show()

        /** binding super directory(root) */
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        supportFragmentManager.beginTransaction().add(R.id.fl_main, Fragment()).commit()

        /** intent */
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.fl_main,
            HomeFragment()
        )
        transaction.commit()
        intent.putExtra("DataList", datas)

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

    override fun onStart() {
        super.onStart()

        Toast.makeText(this, "onStart",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(this, "onResume",Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()

        Toast.makeText(this, "onRestart",Toast.LENGTH_SHORT).show()
    }

    /** Remove regist,User Data, Stop GPSTrackerService Data */
    override fun onStop() {
        super.onStop()

        Toast.makeText(this, "onStop",Toast.LENGTH_SHORT).show()

    }

    /** Remove regist,User Data */
    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "onDestroy",Toast.LENGTH_SHORT).show()
    }
}

