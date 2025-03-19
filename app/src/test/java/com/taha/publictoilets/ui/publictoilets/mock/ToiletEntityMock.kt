package com.taha.publictoilets.ui.publictoilets.mock

import com.taha.domain.entities.ToiletEntity


internal val DefaultToiletEntityMock = ToiletEntity(
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

