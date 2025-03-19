package com.taha.data.repository

import com.taha.data.api.ApiService
import com.taha.data.mapper.toEntities
import com.taha.data.mapper.toEntity
import com.taha.domain.entities.ToiletEntity
import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class PublicToiletsRepositoryImpl @Inject constructor(
  private val api: ApiService,
) : PublicToiletsRepository {

  override suspend fun getToilets(start: Int, rows: Int): Result<List<ToiletEntity>> = runCatching {
    val response = api.getToilets(start = start, rows = rows)
    response.records.toEntities()
  }.onFailure {
    Result.failure<List<ToiletEntity>>(it)
  }

  override suspend fun getToiletDetails(toiletId: String): Result<ToiletEntity> =
    runCatching {
      val response = api.getToiletDetails(toiletId = toiletId)
      val toiletRecord = response.records.firstOrNull()
      toiletRecord?.toEntity() ?: throw NoSuchElementException("No toilet record found for ID: $toiletId")
    }.onFailure {
      return Result.failure(it)
    }
}