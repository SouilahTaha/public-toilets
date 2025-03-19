package com.taha.data.dto

data class GeoShapeDto(
    val coordinates: List<List<Double>>,
    val type: String
)