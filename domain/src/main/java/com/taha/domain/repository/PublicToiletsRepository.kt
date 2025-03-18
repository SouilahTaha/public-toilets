package com.taha.domain.repository

import com.taha.domain.entities.ToiletEntity

interface PublicToiletsRepository {
    suspend fun getPublicToilets():  Result<List<ToiletEntity>>
}