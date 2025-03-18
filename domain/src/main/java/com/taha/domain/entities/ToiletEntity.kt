package com.taha.domain.entities

data class ToiletEntity(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val name: String,
    val schedule: String,
    val isAccessible: String,//todo change to boolean
    val arrondissement: Int,
    val babyArea: String? = null
)
