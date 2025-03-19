package com.taha.data.mapper

import com.taha.data.dto.RecordDto
import com.taha.domain.entities.ToiletEntity

internal fun RecordDto.toEntity() = ToiletEntity(
  id = recordId,
  latitude = fields.geoPoint2d[0],
  longitude = fields.geoPoint2d[1],
  address = fields.adresse,
  name = fields.gestionnaire,
  schedule = fields.horaire,
  isAccessible = fields.accesPmr,
  district = fields.arrondissement,
  babyArea = fields.relaisBebe
)

internal fun List<RecordDto>.toEntities() = this.map { it.toEntity() }