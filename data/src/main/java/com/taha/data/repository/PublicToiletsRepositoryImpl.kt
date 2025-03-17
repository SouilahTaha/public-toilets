package com.taha.data.repository

import com.taha.data.api.ApiService
import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class PublicToiletsRepositoryImpl @Inject constructor(
    private val api: ApiService,
) : PublicToiletsRepository {

    override suspend fun getPublicToilets(): Result<List<Any>> {
        return runCatching {
            // val response =
            api.getToilets()
            //response.records.toToilets() // Map API response to Toilet list
        }.onFailure {
            Result.failure<List<Any>>(it)
        }
    }
}