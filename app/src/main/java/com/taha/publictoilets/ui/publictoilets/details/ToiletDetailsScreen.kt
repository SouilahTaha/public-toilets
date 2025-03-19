package com.taha.publictoilets.ui.publictoilets.details

import ToiletDetailsUiState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Accessible
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.taha.design_system.theme.IconSize
import com.taha.design_system.theme.LargePadding
import com.taha.design_system.theme.NormalPadding
import com.taha.publictoilets.R
import com.taha.publictoilets.extenstions.calculateDistance
import com.taha.publictoilets.extenstions.getCurrentLocation
import com.taha.publictoilets.extenstions.toLatLng
import com.taha.publictoilets.ui.component.Error
import com.taha.publictoilets.ui.component.Loader
import com.taha.publictoilets.ui.component.ToolbarWithTitle
import com.taha.publictoilets.uimodel.defaultToiletMock
import com.taha.publictoilets.uimodel.noDistanceToiletMock
import com.taha.publictoilets.uimodel.notAccessibleToiletMock

@Composable
internal fun ToiletDetailsScreen(
  viewModel: ToiletDetailsViewModel = hiltViewModel(),
  navigateToBack: () -> Unit
) {
  val toiletDetailsUiState by viewModel.getToiletDetailsUiState().collectAsState()
  Scaffold(
    topBar = {
      ToolbarWithTitle(
        title = stringResource(R.string.details_screen_title),
        navigation = navigateToBack,
        navigationIcon = {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.details_screen_back_buton_description)
          )
        },
      )
    }
  ) { innerPadding ->
    when (val currentState = toiletDetailsUiState) {
      is ToiletDetailsUiState.Loading -> Loader()
      is ToiletDetailsUiState.Error -> Error {}
      is ToiletDetailsUiState.Success -> ToiletDetailsSuccessContent(
        toiletDetailsUiState = currentState,
        modifier = Modifier.padding(innerPadding)
      )
    }
  }
}

@Composable
private fun ToiletDetailsSuccessContent(
  toiletDetailsUiState: ToiletDetailsUiState.Success,
  modifier: Modifier = Modifier
) {
  val toilet = toiletDetailsUiState.toilet
  val context = LocalContext.current
  var userLocation by remember { mutableStateOf<LatLng?>(null) }
  context.getCurrentLocation { location ->
    userLocation = location.toLatLng()
  }
  val babyAreaAvailabilityString =
    if (toilet.babyArea)
      stringResource(R.string.toilet_details_baby_area_text)
    else
      stringResource(R.string.toilet_details_baby_area_text)

  val accessibleString =
    if (toilet.isPrmAccessible)
      stringResource(R.string.toilet_details_accessible_text)
    else
      stringResource(R.string.toilet_details_not_accessible_text)

  val accessibleColor =
    if (toilet.isPrmAccessible)
      MaterialTheme.colorScheme.tertiary
    else
      MaterialTheme.colorScheme.error

  Column(
    modifier = modifier
      .padding(LargePadding)
      .verticalScroll(rememberScrollState())
      .fillMaxWidth()
  ) {
    DetailRow(
      icon = Icons.Filled.LocationOn,
      label = stringResource(R.string.toilet_details_address_label),
      value = "${toilet.address}\n${toilet.district}"
    )

    Spacer(modifier = Modifier.height(LargePadding))
    DetailRow(
      icon = Icons.Filled.Schedule,
      label = stringResource(R.string.toilet_details_opening_hours_text),
      value = toilet.openingHours
    )
    Spacer(modifier = Modifier.height(LargePadding))
    DetailRow(
      icon = Icons.Filled.ChildCare,
      label = stringResource(R.string.toilet_details_baby_area_label),
      value = babyAreaAvailabilityString,
    )
    Spacer(modifier = Modifier.height(LargePadding))
    DetailRow(
      icon = Icons.AutoMirrored.Filled.Accessible,
      label = stringResource(R.string.toilet_details_accessible_label),
      value = accessibleString,
      color = accessibleColor
    )
    Spacer(modifier = Modifier.height(LargePadding))
    userLocation?.let {
      val distance = it.calculateDistance(toilet.location)
      DetailRow(
        icon = Icons.Filled.LocationOn,
        label = stringResource(R.string.toilet_details_distance_text),
        value = stringResource(R.string.toilets_item_distance_prefix_text, distance)
      )
    }
  }
}

@Composable
fun DetailRow(
  icon: ImageVector,
  label: String,
  value: String,
  color: Color = MaterialTheme.colorScheme.primary
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      modifier = Modifier.size(IconSize),
      tint = color
    )
    Spacer(modifier = Modifier.size(LargePadding))
    Column {
      Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.size(NormalPadding))
      Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }

  }
}

@Preview(showBackground = true)
@Composable
fun ToiletDetailsScreenPreview() {
  Surface {
    ToiletDetailsSuccessContent(
      toiletDetailsUiState = ToiletDetailsUiState.Success(defaultToiletMock)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun ToiletDetailsScreenPreview_NotAccessible() {

  Surface {
    ToiletDetailsSuccessContent(
      toiletDetailsUiState = ToiletDetailsUiState.Success(notAccessibleToiletMock)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun ToiletDetailsScreenPreview_NoDistance() {
  Surface {
    ToiletDetailsSuccessContent(
      toiletDetailsUiState = ToiletDetailsUiState.Success(noDistanceToiletMock)
    )
  }
}