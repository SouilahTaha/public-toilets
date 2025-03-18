import com.google.android.gms.maps.model.LatLng
import com.taha.publictoilets.uimodel.PublicToiletUiModel

sealed class PublicToiletsUiState {
  data object Error : PublicToiletsUiState()
  data object Loading : PublicToiletsUiState()
  data class Success(
    val toilets: List<PublicToiletUiModel>,
    val userLocation: LatLng? = null,
    val viewType: ViewType = ViewType.LIST
  ) : PublicToiletsUiState()
}

enum class ViewType {
  LIST,
  MAP
}