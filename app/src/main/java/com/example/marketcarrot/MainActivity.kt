package com.example.marketcarrot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.marketcarrot.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.example.marketcarrot.location.MyLocation
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    /** view binding */
    private lateinit var binding: ActivityMainBinding
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    private lateinit var longitude: String
    private lateinit var latitude: String

    /** KAKAO API */
    private val kakaoLocalApi = KakaoApiRetrofitClient.mylocalService

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
        fragmentTransaction.replace(R.id.fl_main, homeFragment)
        fragmentTransaction.commit()
    }

    val datas: ArrayList<Data> = arrayListOf(
        Data("첫번째 제품", "상일동", "20,000", R.drawable.im_1),
        Data("두번째 제품", "중랑구 목동", "46,000", R.drawable.im_2),
        Data("세번째 제품", "하남시 미사1동", "82,000", R.drawable.im_3),
        Data("네번째 제품", "가동", "25,000", R.drawable.im_4),
        Data("다섯번째 제품", "나동", "46,000", R.drawable.im_5),
        Data("여섯번째 제품", "사동", "9,300", R.drawable.im_6),
        Data("일곱번째 제품", "다동", "256,000", R.drawable.im_7),
        Data("여덞번째 제품", "마동", "134,000", R.drawable.im_8),
        Data("아홉번째 제품", "상일동", "53,000", R.drawable.im_9),
        Data("열번째 제품", "명일동", "68,000", R.drawable.im_10),
        Data("열한번째 제품", "강동구", "60,000", R.drawable.im_11),
        Data("열두번째 제품", "중동", "83,000", R.drawable.im_12),
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
                } as Fragment
            )
            true
        }

        mLocationRequest =  LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        binding.btnPlus.setOnClickListener {
            if (checkPermissionForLocation(this)) {
                startLocationUpdates()
            }
        }
    }

    private fun startLocationUpdates() {

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        Looper.myLooper()?.let {
            mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    /** 시스템으로 부터 위치 정보를 콜백으로 받음 */
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    /** 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드 */
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        longitude = "${mLastLocation.longitude}" // 갱신 된 경도
        latitude = "${mLastLocation.latitude}" // 갱신 된 위도
        Log.i("myLocation", "${longitude}\n${latitude}")
        callKakaoKeyword(longitude, latitude)
    }

    /** 카카오 API를 req, res 하는 곳 */
    fun callKakaoKeyword (longitude: String, latitude: String) {
        val mylocation = MutableLiveData<MyLocation>()

        kakaoLocalApi.getKakaoMyAddress(KakaoApi.API_KEY, longitude = longitude, latitude= latitude)
            .enqueue(object : retrofit2.Callback<MyLocation> {
                override fun onResponse(call: Call<MyLocation>, response: Response<MyLocation>) {
                    mylocation.value = response.body()
                    try {
                        Log.i("myLocation", "${response.body()}")
                    } catch (e:Exception) {
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: Call<MyLocation>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    /** 위치 권한이 있는지 확인하는 메서드 */
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    /** 사용자에게 권한 요청 후 결과에 대한 처리 로직 */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()

            } else {
                Log.d("Not Accessed", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

//        Toast.makeText(this, "onStart",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()

//        Toast.makeText(this, "onResume",Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()

//        Toast.makeText(this, "onRestart",Toast.LENGTH_SHORT).show()
    }

    /** Remove regist,User Data, Stop GPSTrackerService Data */
    override fun onStop() {
        super.onStop()

//        Toast.makeText(this, "onStop",Toast.LENGTH_SHORT).show()
    }

    /** Remove regist,User Data */
    override fun onDestroy() {
        super.onDestroy()

//        Toast.makeText(this, "onDestroy",Toast.LENGTH_SHORT).show()
    }

    fun View.show() {
        this.visibility=VISIBLE
    }
    fun View.makeInvisible() {
        this.visibility=INVISIBLE
    }
}

