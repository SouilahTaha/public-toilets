import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLng
import com.taha.publictoilets.R
import com.taha.publictoilets.uimodel.PublicToiletUiModel

sealed class PublicToiletsUiState {
  data object Error : PublicToiletsUiState()
  data object Loading : PublicToiletsUiState()
  data class Success(
    val toilets: List<PublicToiletUiModel>,
    val page: Int = 0,
    val canPaginate: Boolean = true,
    val userLocation: LatLng? = null,
    val viewType: ViewType = ViewType.LIST
  ) : PublicToiletsUiState()
}

enum class ViewType(@StringRes val stringRes:  Int) {
  LIST(R.string.public_toilets_list_tab_label),
  MAP(R.string.public_toilets_map_tab_label)
}