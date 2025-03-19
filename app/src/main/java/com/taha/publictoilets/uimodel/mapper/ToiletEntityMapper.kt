package com.taha.publictoilets.uimodel.mapper

import com.google.android.gms.maps.model.LatLng
import com.taha.domain.entities.ToiletEntity
import com.taha.publictoilets.uimodel.ToiletUiModel
import java.text.DecimalFormat

internal fun List<ToiletEntity>.toToiletsUiModel() = this.map { it.toPublicToiletUiModel() }

internal fun ToiletEntity.toPublicToiletUiModel(): ToiletUiModel {
  return ToiletUiModel(
    toiletId = this.id,
    address = this.address,
    district = this.district.toString(),
    openingHours = this.schedule,
    isPrmAccessible = this.isAccessible.lowercase() == "oui",
    babyArea = this.babyArea?.lowercase() == "oui",
    location = LatLng(this.latitude, this.longitude)
  )
}

internal fun Double.formatDistance(): String {
  val decimalFormat = DecimalFormat("#.##")
  return "${decimalFormat.format(this)} Km"
}