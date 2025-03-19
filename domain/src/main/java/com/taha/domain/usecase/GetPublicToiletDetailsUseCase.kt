package com.taha.domain.usecase

import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class GetPublicToiletDetailsUseCase @Inject constructor(
  private val repository: PublicToiletsRepository
) {

  suspend operator fun invoke(toiletId: String) = repository.getPublicToiletDetails(toiletId = toiletId)
}