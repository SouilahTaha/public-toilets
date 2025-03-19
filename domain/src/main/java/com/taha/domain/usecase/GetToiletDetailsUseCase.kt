package com.taha.domain.usecase

import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class GetToiletDetailsUseCase @Inject constructor(
  private val repository: PublicToiletsRepository
) {

  suspend operator fun invoke(toiletId: String) = repository.getToiletDetails(toiletId = toiletId)
}