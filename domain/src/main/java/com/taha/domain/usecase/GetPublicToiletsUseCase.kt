package com.taha.domain.usecase

import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class GetPublicToiletsUseCase @Inject constructor(
  private val repository: PublicToiletsRepository
) {
  companion object {
    private const val DEFAULT_PAGE = 0
    private const val DEFAULT_SIZE = 10
  }

  suspend operator fun invoke(
    page: Int = DEFAULT_PAGE,
    pageSize: Int = DEFAULT_SIZE
  ) = repository.getPublicToilets(
      start = page * pageSize,
      rows = pageSize
    )
}