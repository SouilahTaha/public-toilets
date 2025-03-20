package com.taha.publictoilets.extenstions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

internal fun Context.openMap(latitude: Double, longitude: Double) {
  val uri = "geo:$latitude,$longitude?q=$latitude,$longitude"
  val intent = Intent(Intent.ACTION_VIEW, uri.toUri())
  startActivity(intent)
}

internal fun Context.getCurrentLocation(onLocation: (Location) -> Unit) {
  val fusedLocationClient: FusedLocationProviderClient =
    LocationServices.getFusedLocationProviderClient(this)
  try {
    fusedLocationClient
      .lastLocation
      .addOnSuccessListener { location: Location? ->
        location?.let(onLocation)
      }
  } catch (e: SecurityException) {
    Toast.makeText(this, "Security Exception: ${e.message}", Toast.LENGTH_SHORT).show()
  }
}

internal fun Context.checkLocationPermission(): Boolean {
  return ContextCompat.checkSelfPermission(
    /* context = */ this,
    /* permission = */ Manifest.permission.ACCESS_FINE_LOCATION
  ) == PackageManager.PERMISSION_GRANTED
}