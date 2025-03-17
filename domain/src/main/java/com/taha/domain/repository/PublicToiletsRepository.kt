package com.taha.domain.repository

interface PublicToiletsRepository {
    suspend fun getPublicToilets():  Result<List<Any>>
}