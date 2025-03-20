package com.taha.publictoilets.feature.public_toilets

sealed class ToiletsUiEvent {
  data class NavigateToToiletDetails(val toiletId: String) : ToiletsUiEvent()
}