sealed class PublicToiletsUiEvent {
  data class NavigateToToiletDetails(val toiletId: String) : PublicToiletsUiEvent()
}