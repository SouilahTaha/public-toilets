sealed class ToiletsUiEvent {
  data class NavigateToToiletDetails(val toiletId: String) : ToiletsUiEvent()
}