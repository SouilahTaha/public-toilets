package com.taha.publictoilets.uimodel.mapper

import com.google.android.gms.maps.model.LatLng
import com.taha.domain.entities.ToiletEntity
import com.taha.publictoilets.extenstions.calculateDistance
import com.taha.publictoilets.uimodel.ToiletUiModel

internal fun List<ToiletEntity>.toToiletsUiModel() = this.map { it.toPublicToiletUiModel() }

internal fun ToiletEntity.toPublicToiletUiModel(): ToiletUiModel {
  val location = LatLng(this.latitude, this.longitude)

  return ToiletUiModel(
    toiletId = this.id,
    address = this.address,
    district = this.district.toString(),
    openingHours = this.schedule,
    isPrmAccessible = this.isAccessible.lowercase() == "oui",
    babyArea = this.babyArea?.lowercase() == "oui",
    location = location,
    distance = calculateUserLocation(location, this.userLatitude, this.userLongitude)
  )
}

private fun calculateUserLocation(toiletLocation: LatLng, latitude: Double?, longitude: Double?): Float? {
  val currentLatitude = latitude ?: return null
  val currentLongitude = longitude ?: return null
  return toiletLocation.calculateDistance(LatLng(currentLatitude, currentLongitude))
}