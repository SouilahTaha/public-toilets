package com.taha.domain.entities

data class ToiletEntity(
  val id: String,
  val latitude: Double,
  val longitude: Double,

  val address: String,
  val name: String,
  val schedule: String,
  val isAccessible: String,
  val district: Int,
  val babyArea: String? = null,
  val userLatitude: Double?= null,
  val userLongitude: Double?= null,
)
