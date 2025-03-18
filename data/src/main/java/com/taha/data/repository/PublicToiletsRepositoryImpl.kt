package com.taha.data.repository

import com.taha.data.api.ApiService
import com.taha.data.mapper.toEntities
import com.taha.domain.entities.ToiletEntity
import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class PublicToiletsRepositoryImpl @Inject constructor(
  private val api: ApiService,
) : PublicToiletsRepository {

  override suspend fun getPublicToilets(): Result<List<ToiletEntity>> {
    return runCatching {
      val response = api.getToilets()
      response.records.toEntities()
    }.onFailure {
      Result.failure<List<Any>>(it)
    }
  }
}