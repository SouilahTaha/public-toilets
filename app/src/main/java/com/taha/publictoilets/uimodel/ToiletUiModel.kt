package com.taha.publictoilets.uimodel

import com.google.android.gms.maps.model.LatLng

data class ToiletUiModel(
  val toiletId: String,
  val address: String,
  val openingHours: String,
  val district: String,
  val isPrmAccessible: Boolean,
  val babyArea: Boolean,
  val location: LatLng,
  val distance: Float? = null
)