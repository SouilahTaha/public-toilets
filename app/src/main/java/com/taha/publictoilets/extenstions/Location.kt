package com.taha.publictoilets.extenstions

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import kotlin.math.round

internal fun Location.toLatLng() = LatLng(this.latitude, this.longitude)

internal fun LatLng.calculateDistance(location: LatLng): Float {
  val results = FloatArray(1)
  Location.distanceBetween(
    /* startLatitude = */ this.latitude,
    /* startLongitude = */ this.longitude,
    /* endLatitude = */ location.latitude,
    /* endLongitude = */ location.longitude,
    /* results = */ results
  )
  val distanceInKm = results[0] / 1000
  return round(distanceInKm * 10) / 10
}