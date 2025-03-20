@file:OptIn(ExperimentalMaterial3Api::class)

package com.taha.publictoilets.feature.public_toilets.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.LatLng
import com.taha.design_system.component.ToiletsBottomSheet
import com.taha.design_system.theme.LargePadding
import com.taha.publictoilets.NO_OPERATION
import com.taha.publictoilets.uimodel.ToiletUiModel
import com.taha.publictoilets.uimodel.defaultToiletMock

@Composable
internal fun DetailsBottomSheet(
  sheetState: SheetState,
  toilet: ToiletUiModel,
  userLocation: LatLng?,
  onDismiss: () -> Unit,
  onToiletClick: (String) -> Unit,
) = ToiletsBottomSheet(
  sheetState = sheetState,
  onDismiss = onDismiss,
) {
  DetailsContent(userLocation, toilet, onToiletClick)
}

@Composable
private fun DetailsContent(
  userLocation: LatLng?,
  toilet: ToiletUiModel,
  onToiletClick: (String) -> Unit
) {
  Column(modifier = Modifier.padding(LargePadding)) {
    ToiletUiModelItem(
      userLocation = userLocation,
      toilet = toilet,
      onToiletClick = onToiletClick
    )
  }
}

@Preview
@Composable
fun DetailsContentPreview() {
  DetailsContent(
    userLocation = LatLng(34.0522, -118.2437),
    toilet = defaultToiletMock,
    onToiletClick = { NO_OPERATION }
  )
}