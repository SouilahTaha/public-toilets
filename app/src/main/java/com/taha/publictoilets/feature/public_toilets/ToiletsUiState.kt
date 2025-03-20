package com.taha.publictoilets.feature.public_toilets

import androidx.annotation.StringRes
import com.google.android.gms.maps.model.LatLng
import com.taha.publictoilets.R
import com.taha.publictoilets.uimodel.ToiletUiModel

sealed class ToiletsUiState {
  data object Error : ToiletsUiState()
  data object Loading : ToiletsUiState()
  data class Success(
    val toilets: List<ToiletUiModel>,
    val page: Int = 0,
    val canPaginate: Boolean = true,
    val userLocation: LatLng? = null,
    val viewType: ViewType = ViewType.LIST
  ) : ToiletsUiState()
}

enum class ViewType(@StringRes val stringRes: Int) {
  LIST(R.string.toilets_list_tab_label),
  MAP(R.string.toilets_map_tab_label)
}