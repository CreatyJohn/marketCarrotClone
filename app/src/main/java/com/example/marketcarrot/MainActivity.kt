package com.example.marketcarrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketcarrot.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

//data class HomeList(val name: String, val contents: String)
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

    /** view binding setting in onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {

        /** binding super directory(root) */
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("LifeCycle", "OnCreate")

        /** RecyclerView Data Adapting, Like a pointer(포인터 함수) */
        val dataList: MutableList<Data> = mutableListOf(
            Data("문 팝니다", R.drawable.im_1),
            Data("책상 팝니다", R.drawable.im_2),
            Data("자전거 팝니다", R.drawable.im_3),
            Data("스팸 팝니다", R.drawable.im_4),
            Data("전기자전거 팝니다", R.drawable.im_5),
            Data("갤럭시 워치 팝니다", R.drawable.im_6),
            Data("치마 팝니다", R.drawable.im_7),
            Data("핸드폰 팝니다", R.drawable.im_8),
            Data("애기 자전거 팝니다", R.drawable.im_9),
            Data("난로 팝니다", R.drawable.im_10),
            Data("캠핑장 팝니다", R.drawable.im_11),
            Data("세탁기 팝니다", R.drawable.im_12),
        )

        val adapter = DataRVAdapter(dataList)
        binding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter

        /** bottomNavigation setting in onCreate */
        //fragment section

//        /** Animation of Navbar New Data badges*/
//        var badge = bn.getOrCreateBadge(menuItemId)
//        badge.isVisible = true
//        // An icon only badge will be displayed unless a number is set:
//        badge.number = 99
//
//        val badgeDrawable = bn.getBadge(menuItemId)
//        if (badgeDrawable != null) {
//            badgeDrawable.isVisible = false
//            badgeDrawable.clearNumber()
//        }
//
//        bn.removeBadge(menuItemId)

        supportFragmentManager.beginTransaction().add(fl_main.id, HomeFragment()).commit()

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

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(fl_main.id, fragment).commit()
    }

    /**앱 시작마다 필요한 동네 GPS 설정*/
    override fun onStart() {
        super.onStart()

        startService(GpsTrackerService.getIntent(this@MainActivity))

        Toast.makeText(this, "시작합니다",Toast.LENGTH_SHORT).show()

        /** SplashScreen() */
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

