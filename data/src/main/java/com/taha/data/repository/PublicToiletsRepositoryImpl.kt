package com.taha.data.repository

import com.taha.data.api.ApiService
import com.taha.data.dto.ToiletDetailsResponseDto
import com.taha.data.mapper.toEntities
import com.taha.data.mapper.toEntity
import com.taha.domain.entities.ToiletEntity
import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class PublicToiletsRepositoryImpl @Inject constructor(
  private val api: ApiService,
) : PublicToiletsRepository {

  override suspend fun getPublicToilets(start: Int, rows: Int): Result<List<ToiletEntity>> = runCatching {
    val response = api.getToilets(start = start, rows = rows)
    response.records.toEntities()
  }.onFailure {
    Result.failure<List<ToiletEntity>>(it)
  }

  override suspend fun getPublicToiletDetails(toiletId: String): Result<ToiletEntity> =
    runCatching {
      val response: ToiletDetailsResponseDto = api.getToiletDetails(toiletId = toiletId)
      response.record.toEntity()
    }.onFailure {
      return Result.failure<ToiletEntity>(it)
    }
}