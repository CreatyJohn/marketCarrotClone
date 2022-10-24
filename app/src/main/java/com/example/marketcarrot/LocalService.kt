package com.example.marketcarrot

import com.example.marketcarrot.location.MyLocation
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

interface KakaoMyLocalService {
    @GET("v2/local/geo/coord2regioncode.json")
    fun getKakaoMyAddress(
        @Header("Authorization") key: String,
        @Query("query") longitude: String,
        @Query("query") latitude: String
    ): Call<MyLocation>
}