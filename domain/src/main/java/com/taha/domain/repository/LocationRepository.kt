package com.taha.domain.repository

interface LocationRepository {
  suspend fun getUserLocation(): Pair<Double, Double>?
  suspend fun updateUserLocation(lat: Double, long: Double)
}