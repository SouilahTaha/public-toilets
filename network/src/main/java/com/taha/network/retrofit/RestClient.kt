package com.taha.network.retrofit

import com.taha.data.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://data.ratp.fr/api/records/1.0"

private val retrofit: Retrofit
  get() = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api: ApiService = retrofit.create(ApiService::class.java)