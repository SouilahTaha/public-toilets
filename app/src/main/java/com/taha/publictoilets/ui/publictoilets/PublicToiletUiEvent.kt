sealed class PublicToiletsUiEvent {
  data class NavigateToToiletDetails(val toiletId: String) : PublicToiletsUiEvent()
  data class ShowSnackbar(val message: String) : PublicToiletsUiEvent()
}