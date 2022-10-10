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

    /** 향후 데이터에서 서버를 받았을때 아래와 같은 ArrayList 형태로 받아서 CRUD를 구축하려고 함*/
    val dataList: ArrayList<Data> = arrayListOf(

        Data("이사가면서 해결해요, 브라운컬러", "강동구 천호제3동", "20,000", R.drawable.im_1),
        Data("이케아 수제 책상 (목제)", "송파구 풍납동", "46,000", R.drawable.im_2),
        Data("MTB 자전거 3500-X", "강동구 성내동", "82,000", R.drawable.im_3),
        Data("스팸 10개 세트", "강동구 성내동","25,000", R.drawable.im_4),
        Data("전기자전거 급처합니다.", "강동구 암사동","120,000", R.drawable.im_5),
        Data("갤럭시워치5 미개봉", "하남시 미사동","170,000", R.drawable.im_6),
        Data("유니클로 치마 28사이즈", "명일동", "7,000", R.drawable.im_7),
        Data("갤럭시 S21 1년 중고", "풍산동", "200,000", R.drawable.im_8),
        Data("유아용 자전거 3에 처분", "하남시 위례동", "30,000", R.drawable.im_9),
        Data("위디아 난로", "강일동", "50,000", R.drawable.im_10),
        Data("캠핑장 대여해드립니다!!", "서울특별시 송파구", "30,000", R.drawable.im_11),
        Data("LG 16년식 세탁기", "송파구 방이2동", "250,000", R.drawable.im_12)

    )

    /** view binding setting in onCreate */
    override fun onCreate(savedInstanceState: Bundle?) {

        /** binding super directory(root) */
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        supportFragmentManager.beginTransaction().add(fl_main.id, Fragment()).commit()

        /** intent로 리스트데이터를 넘긴다. */
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.listFrame,
            HomeFragment()
        )
        transaction.commit()
        intent.putExtra("DataList", dataList)

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

//        startService(GpsTrackerService.getIntent(this@MainActivity))

        Toast.makeText(this, "시작합니다",Toast.LENGTH_SHORT).show()
    }

    /**어플의 기능을 정의하는 곳.. ex) 유저의 정보, 게시물을 정의할 예정 */
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

//        GpsTrackerService.stopTracking(this@MainActivity)

        Toast.makeText(this, "onStop",Toast.LENGTH_SHORT).show()

    }

    /** Remove regist,User Data */
    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "onDestroy",Toast.LENGTH_SHORT).show()
    }
}

