package com.example.marketcarrot

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class KakaoApi {
    companion object {
        const val API_KEY = "KakaoAK 841e454f678916ca564040940fbacc3e"
        const val BASE_URL = "https://dapi.kakao.com/"
    }
}

interface KakaoApiService {
    @GET("v2/local/search/address.json")
    fun getKakaoAddress(
        @Header("Authorization") key: String,
        @Query("query") address: String
    ): Call<KakaoData>
}