package com.taha.data.mock

import com.taha.data.dto.FieldsDto
import com.taha.data.dto.GeoShapeDto
import com.taha.data.dto.GeometryDto
import com.taha.data.dto.RecordDto
import com.taha.data.dto.ToiletResponseDto

internal val DefaultRecordDtoMock = RecordDto(
  datasetId = "dataset1",
  recordId = "1",
  fields = FieldsDto(
    complementAddress = "Address Comp 1",
    geoShape = GeoShapeDto(listOf(listOf(1.0, 2.0), listOf(3.0, 4.0)), "Polygon"),
    horaire = "24/7",
    accesPmr = "yes",
    arrondissement = 1,
    geoPoint2d = listOf(1.0, 2.0),
    source = "source1",
    gestionnaire = "gestionnaire1",
    adresse = "address1",
    type = "type1",
    urlFicheEquipement = "url1",
    relaisBebe = "yes"
  ),
  geometry = GeometryDto("Point", listOf(1.0, 2.0)),
  recordTimestamp = "2023-01-01T00:00:00Z"
)

internal val NonAccessibleRecordDtoMock = RecordDto(
  datasetId = "dataset2",
  recordId = "2",
  fields = FieldsDto(
    complementAddress = "Address Comp 2",
    geoShape = GeoShapeDto(listOf(listOf(5.0, 6.0), listOf(7.0, 8.0)), "Polygon"),
    horaire = "8am-6pm",
    accesPmr = "no",
    arrondissement = 2,
    geoPoint2d = listOf(3.0, 4.0),
    source = "source2",
    gestionnaire = "gestionnaire2",
    adresse = "address2",
    type = "type2",
    urlFicheEquipement = "url2",
    relaisBebe = "no"
  ),
  geometry = GeometryDto("Point", listOf(3.0, 4.0)),
  recordTimestamp = "2023-01-02T00:00:00Z"
)
internal val ToiletResponseDtoMock = ToiletResponseDto(
  records = listOf(
    DefaultRecordDtoMock,
    NonAccessibleRecordDtoMock
  )
)

internal val ToiletDetailsResponseDtoMock = ToiletResponseDto(records = listOf(DefaultRecordDtoMock))

internal val EmptyToiletResponseDtoMock = ToiletResponseDto(records = emptyList())