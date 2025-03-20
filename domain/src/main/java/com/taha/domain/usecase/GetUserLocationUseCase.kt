package com.taha.domain.usecase

import com.taha.domain.repository.LocationRepository
import javax.inject.Inject

class SetUserLocationUseCase @Inject constructor(
  private val locationRepository: LocationRepository
) {
  suspend operator fun invoke(lat: Double, long: Double) =
    locationRepository.updateUserLocation(lat = lat, long = long)

}