package com.taha.publictoilets.ui.publictoilets

sealed class ToiletsUiEvent {
  data class NavigateToToiletDetails(val toiletId: String) : ToiletsUiEvent()
}