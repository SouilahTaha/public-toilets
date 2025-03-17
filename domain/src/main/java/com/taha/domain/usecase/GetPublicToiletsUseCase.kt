package com.taha.domain.usecase

import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class GetPublicToiletsUseCase @Inject constructor(
    private val repository: PublicToiletsRepository
) {
    suspend operator fun invoke(): Result<List<Any>> {
        return repository.getPublicToilets()
    }
}