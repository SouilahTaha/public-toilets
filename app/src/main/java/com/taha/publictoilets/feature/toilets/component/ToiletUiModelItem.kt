package com.taha.publictoilets.feature.toilets.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Accessible
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.NotAccessible
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.taha.designsystem.component.ActionButton
import com.taha.designsystem.theme.LargePadding
import com.taha.designsystem.theme.SmallPadding
import com.taha.designsystem.theme.VerySmallPadding
import com.taha.publictoilets.NO_OPERATION
import com.taha.publictoilets.R
import com.taha.publictoilets.extenstions.openMap
import com.taha.publictoilets.uimodel.ToiletUiModel
import com.taha.publictoilets.uimodel.defaultToiletMock

@Composable
internal fun ToiletUiModelItem(
  toilet: ToiletUiModel,
  onToiletClick: (String) -> Unit
) {
  val context = LocalContext.current
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = SmallPadding)
  ) {
    Column(modifier = Modifier.padding(LargePadding)) {
      Address(toilet.address)
      Spacer(modifier = Modifier.size(VerySmallPadding))
      OpeningHour(toilet.openingHours)
      Spacer(modifier = Modifier.size(VerySmallPadding))
      AccessibilityRow(toilet.isPrmAccessible)
      toilet.distance?.let {
        Spacer(modifier = Modifier.size(VerySmallPadding))
        Text(text = stringResource(R.string.toilets_item_distance_prefix_text, it))
      }
      Spacer(modifier = Modifier.size(VerySmallPadding))
      Row {
        ItineraryButton(onClick = {
          context.openMap(
            latitude = toilet.location.latitude,
            longitude = toilet.location.longitude
          )
        })
        Spacer(modifier = Modifier.size(SmallPadding))
        DetailsButton(onClick = { onToiletClick(toilet.toiletId) })
      }
    }
  }
}

@Composable
private fun Address(address: String) {
  Text(
    text = address,
    style = MaterialTheme.typography.bodyLarge
  )
}

@Composable
private fun OpeningHour(openingHours: String) {
  Text(
    text = stringResource(R.string.toilet_item_opening_hours_prefix_text, openingHours),
    style = MaterialTheme.typography.bodyMedium
  )
}

@Composable
private fun AccessibilityRow(isPrmAccessible: Boolean) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    if (isPrmAccessible) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.Accessible,
        contentDescription = "PRM Accessible",
        tint = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.size(LargePadding)
      )
      Spacer(modifier = Modifier.size(VerySmallPadding))
      Text(
        text = stringResource(R.string.toilet_item_accessible_text),
        color = MaterialTheme.colorScheme.tertiary
      )
    } else {
      Icon(
        imageVector = Icons.Filled.NotAccessible,
        contentDescription = "PRM Accessible",
        tint = MaterialTheme.colorScheme.error,
        modifier = Modifier.size(LargePadding)
      )
      Text(
        text = stringResource(R.string.toilet_item_not_accessible_text),
        color = MaterialTheme.colorScheme.error
      )
    }
  }
}

@Composable
internal fun ItineraryButton(onClick: () -> Unit, modifier: Modifier = Modifier) =
  ActionButton(
    icon = Icons.Filled.Directions,
    textResId = R.string.toilet_item_itinerary_text,
    contentDescriptionResId = R.string.toilet_item_itinerary_description,
    modifier = modifier,
    onClick = onClick
  )

@Composable
internal fun DetailsButton(onClick: () -> Unit, modifier: Modifier = Modifier) =
  ActionButton(
    icon = Icons.Filled.Details,
    textResId = R.string.toilet_item_details_text,
    contentDescriptionResId = R.string.toilet_item_details_description,
    modifier = modifier,
    onClick = onClick
  )

@Preview(showBackground = true)
@Composable
private fun PublicToiletUiModelItemPreview() {
  ToiletUiModelItem(
    toilet = defaultToiletMock,
    onToiletClick = { NO_OPERATION }
  )
}