package com.taha.domain.usecase

import com.taha.domain.entities.ToiletEntity
import com.taha.domain.repository.LocationRepository
import com.taha.domain.repository.PublicToiletsRepository
import javax.inject.Inject

class GetToiletDetailsUseCase @Inject constructor(
  private val toiletRepository: PublicToiletsRepository,
  private val locationRepository: LocationRepository

) {

  suspend operator fun invoke(toiletId: String): Result<ToiletEntity> {
    val toiletDetailsResult = toiletRepository.getToiletDetails(toiletId)
    val userLocation = locationRepository.getUserLocation()
    return when {
      toiletDetailsResult.isSuccess -> {
        toiletDetailsResult.map { toilet ->
          toilet.copy(
            userLatitude = userLocation?.first,
            userLongitude = userLocation?.second
          )
        }
      }

      toiletDetailsResult.isFailure -> toiletDetailsResult
      else -> Result.failure(Exception("can't get user location"))
    }
  }
}