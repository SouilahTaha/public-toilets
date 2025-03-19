package com.taha.publictoilets.uimodel.mapper

import com.google.android.gms.maps.model.LatLng
import com.taha.domain.entities.ToiletEntity
import com.taha.publictoilets.uimodel.PublicToiletUiModel
import java.text.DecimalFormat

internal fun List<ToiletEntity>.toPublicToiletsUiModel() = this.map { it.toPublicToiletUiModel() }

internal fun ToiletEntity.toPublicToiletUiModel(): PublicToiletUiModel {
  return PublicToiletUiModel(
    address = this.address,
    openingHours = this.schedule,
    isPrmAccessible = this.isAccessible.lowercase() == "oui",
    location = LatLng(this.latitude, this.longitude)
  )
}

internal fun Double.formatDistance(): String {
  val decimalFormat = DecimalFormat("#.##")
  return "${decimalFormat.format(this)} Km"
}