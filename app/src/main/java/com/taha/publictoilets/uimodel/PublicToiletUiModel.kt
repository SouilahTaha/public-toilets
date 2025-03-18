package com.taha.publictoilets.uimodel

import com.google.android.gms.maps.model.LatLng

data class PublicToiletUiModel(
    val address: String,
    val openingHours: String,
    val isPrmAccessible: Boolean,
    val location: LatLng,
    val distance: String? = null,
)