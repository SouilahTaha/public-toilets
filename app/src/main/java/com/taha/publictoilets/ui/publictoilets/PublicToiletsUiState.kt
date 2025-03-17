import com.google.android.gms.maps.model.LatLng

sealed class PublicToiletsUiState {
  data object Error : PublicToiletsUiState()
  data object Loading : PublicToiletsUiState()
  data class Success(
    val toilets: List<Any> = emptyList(),//todo change to toilet uiModel
    val userLocation: LatLng? = null,
    val viewType: ViewType = ViewType.LIST
  )
}

enum class ViewType {
  LIST,
  MAP
}