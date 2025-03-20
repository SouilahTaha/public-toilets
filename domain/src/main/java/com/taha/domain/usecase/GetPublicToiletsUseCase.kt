package com.taha.domain.usecase

import com.taha.domain.entities.ToiletEntity
import com.taha.domain.repository.LocationRepository
import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class GetPublicToiletsUseCase @Inject constructor(
  private val toiletRepository: PublicToiletsRepository,
  private val locationRepository: LocationRepository
) {
  companion object {
    private const val DEFAULT_PAGE = 0
    private const val DEFAULT_SIZE = 10
  }

  suspend operator fun invoke(
    page: Int = DEFAULT_PAGE,
    pageSize: Int = DEFAULT_SIZE
  ): Result<List<ToiletEntity>> {
    val toiletsResult = toiletRepository.getToilets(page, pageSize)
    val userLocation = locationRepository.getUserLocation()
    return when {
      toiletsResult.isSuccess -> {
        val toiletsWithDistance = toiletsResult.getOrThrow().map { toilet ->
          toilet.copy(userLatitude = userLocation?.first, userLongitude = userLocation?.second)
        }
        Result.success(toiletsWithDistance)
      }

      toiletsResult.isFailure -> toiletsResult
      else -> Result.failure(Exception("can't get user location"))
    }
  }
}