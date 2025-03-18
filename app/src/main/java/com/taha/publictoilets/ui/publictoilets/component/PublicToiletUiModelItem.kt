package com.taha.publictoilets.ui.publictoilets.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Accessible
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.NotAccessible
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.taha.design_system.theme.LargePadding
import com.taha.design_system.theme.SmallPadding
import com.taha.design_system.theme.VerySmallPadding
import com.taha.publictoilets.R
import com.taha.publictoilets.extenstions.openMap
import com.taha.publictoilets.uimodel.PublicToiletUiModel
import com.taha.publictoilets.uimodel.publicToiletsUiModelMock


@Composable
internal fun PublicToiletUiModelItem(toilet: PublicToiletUiModel) {
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
      if (toilet.distance != null) {
        Spacer(modifier = Modifier.size(VerySmallPadding))
        Distance(toilet.distance)
      }
      ItineraryButton(onClick = {
        context.openMap(
          latitude = toilet.location.latitude,
          longitude = toilet.location.longitude
        )
      })
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
    text = stringResource(R.string.public_toilet_item_opening_hours_prefix_text, openingHours),
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
        text = stringResource(R.string.public_toilet_item_accessible_text),
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
        text = stringResource(R.string.public_toilet_item_not_accessible_text),
        color = MaterialTheme.colorScheme.error
      )
    }
  }
}

@Composable
private fun Distance(distance: String) {
  Text(text = stringResource(R.string.public_toilets_item_distance_prefix_text, distance))
}

@Composable
internal fun ItineraryButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier
      .wrapContentSize()
      .clip(RoundedCornerShape(50))
      .background(MaterialTheme.colorScheme.primaryContainer)
      .clickable { onClick() }
      .padding(SmallPadding),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = Icons.Filled.Directions,
      contentDescription = stringResource(R.string.public_toilet_item_itinerary_description),
      tint = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.size(VerySmallPadding))
    Text(
      text = stringResource(R.string.public_toilet_item_itinerary_text),
      color = MaterialTheme.colorScheme.primary,
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun PublicToiletUiModelItemPreview() {
  PublicToiletUiModelItem(publicToiletsUiModelMock.first())
}