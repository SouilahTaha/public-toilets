package com.taha.publictoilets.ui.publictoilets.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.taha.publictoilets.R
import com.taha.publictoilets.uimodel.ToiletUiModel

private val DEFAULT_PARIS_LOCATION = LatLng(48.8584, 2.2945)
private const val CAMERA_ZOOM_DURATION_IN_MS = 1000
private const val CAMERA_DEFAULT_ZOOM = 15f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ToiletsMap(
  userLocation: LatLng?,
  publicToilets: List<ToiletUiModel>,
  onToiletClick: (String) -> Unit,
) {
  val sheetState = rememberModalBottomSheetState()
  var selectedToiletUiModel by remember { mutableStateOf<ToiletUiModel?>(null) }

  val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition.fromLatLngZoom(
      /* target = */ userLocation ?: DEFAULT_PARIS_LOCATION,
      /* zoom = */ 10f
    )
  }
  LaunchedEffect(userLocation) {
    if (userLocation != null) {
      cameraPositionState.animate(
        update = CameraUpdateFactory.newLatLngZoom(userLocation, CAMERA_DEFAULT_ZOOM),
        durationMs = CAMERA_ZOOM_DURATION_IN_MS
      )
    }
  }

  Box(modifier = Modifier.fillMaxSize()) {
    selectedToiletUiModel?.let {
      DetailsBottomSheet(
        sheetState = sheetState,
        toilet = it,
        userLocation = userLocation,
        onToiletClick = onToiletClick,
        onDismiss = { selectedToiletUiModel = null }
      )
    }
    GoogleMap(
      modifier = Modifier.fillMaxSize(),
      cameraPositionState = cameraPositionState,
      properties = MapProperties(isMyLocationEnabled = userLocation != null),
      uiSettings = MapUiSettings(zoomControlsEnabled = false, myLocationButtonEnabled = true)
    ) {
      userLocation?.let {
        Marker(
          contentDescription = stringResource(R.string.toilets_your_position_marker_content_description),
          state = MarkerState(position = it),
          title = stringResource(R.string.toilets_your_position_title),
          snippet = stringResource(R.string.toilets_your_position_description),
        )
      }
      publicToilets.forEach { toilet ->
        Marker(
          state = MarkerState(position = toilet.location),
          title = toilet.address,
          snippet = toilet.openingHours,
          onClick = {
            selectedToiletUiModel = toilet
            true
          }
        )
      }
    }
  }
}