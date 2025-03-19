package com.taha.publictoilets.uimodel


import com.google.android.gms.maps.model.LatLng

internal val defaultToiletMock =
  ToiletUiModel(
    toiletId = "T1",
    address = "123 Rue de Rivoli, Paris",
    openingHours = "8:00 AM - 10:00 PM",
    district = "1st",
    isPrmAccessible = true,
    babyArea = true,
    location = LatLng(48.864716, 2.349014),
  )

internal val notAccessibleToiletMock =
  ToiletUiModel(
    toiletId = "T2",
    address = "456 Avenue des Champs-Élysées, Paris",
    openingHours = "9:00 AM - 9:00 PM",
    district = "8th",
    isPrmAccessible = false,
    babyArea = false,
    location = LatLng(48.873792, 2.295028),
  )

internal val noDistanceToiletMock =
  ToiletUiModel(
    toiletId = "T3",
    address = "789 Rue Montmartre, Paris",
    openingHours = "10:00 AM - 8:00 PM",
    district = "2nd",
    isPrmAccessible = true,
    babyArea = true,
    location = LatLng(48.870073, 2.344799),
  )