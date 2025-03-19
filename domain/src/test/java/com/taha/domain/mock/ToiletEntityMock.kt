package com.taha.domain.mock

import com.taha.domain.entities.ToiletEntity


val DefaultToiletEntityMock = ToiletEntity(
  id = "1",
  latitude = 1.0,
  longitude = 2.0,
  address = "address1",
  name = "gestionnaire1",
  schedule = "24/7",
  isAccessible = "yes",
  district = 1,
  babyArea = "yes"
)

val NonAccessibleToiletEntityMock = ToiletEntity(
  id = "2",
  latitude = 3.0,
  longitude = 4.0,
  address = "address2",
  name = "gestionnaire2",
  schedule = "8am-6pm",
  isAccessible = "no",
  district = 2,
  babyArea = "no"
)

val ToiletEntitiesMock = listOf(
  DefaultToiletEntityMock,
  NonAccessibleToiletEntityMock
)

val ToiletDetailsEntitiesMock = listOf(DefaultToiletEntityMock)
val EmptyToiletDetailsEntitiesMock = emptyList<ToiletEntity>()
