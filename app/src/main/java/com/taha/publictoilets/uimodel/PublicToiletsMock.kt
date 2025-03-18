package com.taha.publictoilets.uimodel


import com.google.android.gms.maps.model.LatLng

val publicToiletsUiModelMock = listOf(
  PublicToiletUiModel(
    address = "123 Rue de Rivoli, Paris",
    openingHours = "8:00 AM - 10:00 PM",
    isPrmAccessible = true,
    location = LatLng(48.864716, 2.349014),
    distance = "1.2 km"
  ),
  PublicToiletUiModel(
    address = "456 Avenue des Champs-Élysées, Paris",
    openingHours = "9:00 AM - 9:00 PM",
    isPrmAccessible = false,
    location = LatLng(48.873792, 2.295028),
    distance = "2.5 km"
  ),
  PublicToiletUiModel(
    address = "789 Rue Montmartre, Paris",
    openingHours = "10:00 AM - 8:00 PM",
    isPrmAccessible = true,
    location = LatLng(48.870073, 2.344799),
    distance = "0.8 km"
  ),
  PublicToiletUiModel(
    address = "101 Rue de la Pompe, Paris",
    openingHours = "8:00 AM - 10:00 PM",
    isPrmAccessible = false,
    location = LatLng(48.861331, 2.280637),
    distance = "1.7 km"
  ),
  PublicToiletUiModel(
    address = "112 Avenue de Wagram, Paris",
    openingHours = "9:00 AM - 9:00 PM",
    isPrmAccessible = true,
    location = LatLng(48.880587, 2.300724),
    distance = "3.1 km"
  ),
)