package com.taha.domain.repository

import com.taha.domain.entities.ToiletEntity

interface PublicToiletsRepository {
  suspend fun getPublicToilets(start: Int, rows: Int): Result<List<ToiletEntity>>
  suspend fun getPublicToiletDetails(toiletId: String): Result<ToiletEntity>
}