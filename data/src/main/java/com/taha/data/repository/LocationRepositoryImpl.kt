package com.taha.data.repository

import android.location.Location
import com.taha.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
) : LocationRepository {
  private var userLocation: Location? = null
  override suspend fun getUserLocation(): Pair<Double, Double>? {
    return userLocation?.let {
      Pair(it.latitude, it.longitude)
    }
  }

  override suspend fun updateUserLocation(lat: Double, long: Double) {
    userLocation = Location("location").apply {
      latitude = lat
      longitude = long
    }
  }
}