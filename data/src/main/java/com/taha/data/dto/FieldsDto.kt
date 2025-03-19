package com.taha.data.dto

import com.google.gson.annotations.SerializedName

data class FieldsDto(
    @SerializedName("complement_adresse") val complementAddress: String?,
    @SerializedName("geo_shape") val geoShape: GeoShapeDto,
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