package com.taha.domain.repository

import com.taha.domain.entities.ToiletEntity

interface PublicToiletsRepository {
  suspend fun getToilets(start: Int, rows: Int): Result<List<ToiletEntity>>
  suspend fun getToiletDetails(toiletId: String): Result<ToiletEntity>
}