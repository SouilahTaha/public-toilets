package com.taha.data.dto

import com.google.gson.annotations.SerializedName


data class ToiletResponse(
    val parameters: Parameters,
    val records: List<Record>
)

data class Parameters(
    val dataset: String,
    val rows: Int,
    val start: Int,
    val format: String,
    val timezone: String
)

data class Record(
    @SerializedName("datasetid") val datasetId: String,
    @SerializedName("recordid") val recordId: String,
    val fields: Fields,
    val geometry: Geometry,
    @SerializedName("record_timestamp") val recordTimestamp: String
)

data class Fields(
    @SerializedName("complement_adresse") val complementAddress: String?,
    @SerializedName("geo_shape") val geoShape: GeoShape,
    val horaire: String,
    @SerializedName("acces_pmr") val accesPmr: String,
    val arrondissement: Int,
    @SerializedName("geo_point_2d") val geoPoint2d: List<Double>,
    val source: String,
    val gestionnaire: String,
    val adresse: String,
    val type: String,
    @SerializedName("url_fiche_equipement") val urlFicheEquipement: String? = null,
    @SerializedName("relais_bebe") val relaisBebe: String? = null
)

data class GeoShape(
    val coordinates: List<List<Double>>,
    val type: String
)

data class Geometry(
    val type: String,
    val coordinates: List<Double>
)

// Mapped Data Class for UI

data class Toilet(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val name: String,
    val schedule: String,
    val isAccessible: String,
    val arrondissement: Int,
    val babyArea: String? = null
)

// Mapper Function

fun Record.toToilet(): Toilet {
    return Toilet(
        id = recordId,
        latitude = fields.geoPoint2d[0],
        longitude = fields.geoPoint2d[1],
        address = fields.adresse,
        name = fields.gestionnaire,
        schedule = fields.horaire,
        isAccessible = fields.accesPmr,
        arrondissement = fields.arrondissement,
        babyArea = fields.relaisBebe
    )
}

fun List<Record>.toToilets(): List<Toilet> {
    return this.map { it.toToilet() }
}