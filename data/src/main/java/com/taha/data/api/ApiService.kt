package com.taha.data.api

import com.taha.data.dto.ToiletResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("api/records/1.0/search")
  suspend fun getToilets(
    @Query("dataset") rows: String = "sanisettesparis2011"
  ): ToiletResponse
}