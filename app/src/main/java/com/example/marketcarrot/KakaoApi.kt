package com.example.marketcarrot

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoApiRetrofitClient {
    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(KakaoApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: KakaoApiService by lazy {
        retrofit
            .build()
            .create(KakaoApiService::class.java)
    }
}