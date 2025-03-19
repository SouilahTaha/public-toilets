package com.taha.publictoilets.ui.publictoilets.mock

import com.taha.domain.entities.ToiletEntity


internal val DefaultToiletEntityMock = ToiletEntity(
  id = "1",
  latitude = 1.0,
  longitude = 2.0,
  address = "address1",
  name = "gestionnaire1",
  schedule = "24/7",
  isAccessible = "oui",
  district = 1,
  babyArea = "oui"
)
internal val NonAccessibleToiletEntityMock = ToiletEntity(
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

internal val ToiletEntitiesMock = listOf(
  DefaultToiletEntityMock,
  NonAccessibleToiletEntityMock
)