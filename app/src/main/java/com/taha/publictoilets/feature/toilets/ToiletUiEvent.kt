package com.taha.publictoilets.feature.toilets

sealed class ToiletsUiEvent {
  data class NavigateToToiletDetails(val toiletId: String) : ToiletsUiEvent()
}