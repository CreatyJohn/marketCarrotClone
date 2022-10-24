package com.example.marketcarrot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.marketcarrot.databinding.ActivityPutlocationSplashBinding
import kotlinx.android.synthetic.main.address_data.*
import retrofit2.Call
import retrofit2.Response

class PutLocationSplash : AppCompatActivity() {

    lateinit var binding: ActivityPutlocationSplashBinding
    lateinit var addressDataRVAdapter: AddressDataRVAdapter
    private val kakaoApi = KakaoApiRetrofitClient.apiService
    private val datas = mutableListOf<AddressData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usercheck_splash)
        binding = ActivityPutlocationSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecycler()

        val search = binding.etSearch.text

        binding.btnMylocation.setOnClickListener {
            datas.removeAll(datas)
            callKakaoKeyword("${search}")
        }
    }

    /** 카카오 API를 req, res 하는 곳 */
    fun callKakaoKeyword (address: String) {
        val kakao = MutableLiveData<KakaoData>()

        kakaoApi.getKakaoAddress(KakaoApi.API_KEY, address = address)
            .enqueue(object : retrofit2.Callback<KakaoData> {
                override fun onResponse(call: Call<KakaoData>, response: Response<KakaoData>) {
                    kakao.value = response.body()

                    val count: Int = kakao.value!!.meta.total_count

                    try {
                        for (i in 0..count) {
                            addTask("${kakao.value!!.documents[i].address_name}")
                        }
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: Call<KakaoData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    /** 배열 담는(추가하는) 곳 */
    private fun initRecycler() {
        addressDataRVAdapter = AddressDataRVAdapter(this)
        binding.rvAddress.adapter = addressDataRVAdapter

        datas.apply {
        }

        addressDataRVAdapter.datas = datas
        addressDataRVAdapter.notifyDataSetChanged()

    }

    /** 외부에서 배열에 추가할 수 있게 사용하는 곳 */
    fun addTask(address: String) {
        //editText에서 사용자가쓴 할일을 가져온다.
        val todo = AddressData(address)
        //가져온 할일을 data arraylist에 추가해줍니다.
        datas.add(todo)

        //아이템이 추가되고 UI가 바뀐걸 업데이트해주는코드
        binding.rvAddress.adapter?.notifyDataSetChanged()
    }

}