package com.taha.data.api

import com.taha.data.dto.ToiletResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("/search")
  suspend fun getToilets(@Query("dataset") one: String = "sanisettesparis2011"): List<ToiletResponse>
}