package com.taha.data.api

import com.taha.data.dto.ToiletDetailsResponseDto
import com.taha.data.dto.ToiletResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("api/records/1.0/search")
  suspend fun getToilets(
    @Query("dataset") dataset: String = "sanisettesparis2011",
    @Query("rows") rows: Int,
    @Query("start") start: Int
  ): ToiletResponseDto

  @GET("api/records/1.0/search")
  suspend fun getToiletDetails(
    @Query("dataset") dataset: String = "sanisettesparis2011",
    @Query("toiletId") toiletId: String,
  ): ToiletDetailsResponseDto
}