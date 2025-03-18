package com.taha.publictoilets.ui.publictoilets.component

import ViewType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.taha.design_system.theme.LargePadding
import com.taha.design_system.theme.SmallPadding
import com.taha.publictoilets.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConfigurationBottomSheet(
  sheetState: SheetState,
  isDefaultModeSelected: Boolean,
  isPrmFilterEnabled: Boolean,
  onModeSelected: (ViewType) -> Unit,
  onFilterClick: () -> Unit,
  onDismiss: () -> Unit,
) = ModalBottomSheet(
  sheetState = sheetState,
  onDismissRequest = { onDismiss.invoke() },
) {
  val accessibilityFilterText = if (isPrmFilterEnabled)
    stringResource(R.string.configuration_bottom_sheet_accessible_filter_selected_label)
  else
    stringResource(R.string.configuration_bottom_sheet_accessible_filter_default_label)

  Column(modifier = Modifier.padding(LargePadding)) {
    Text(
      color = MaterialTheme.colorScheme.tertiary,
      style = MaterialTheme.typography.bodyLarge,
      text = "View Mode"
    )
    Spacer(modifier = Modifier.height(SmallPadding))
    Row {
      FilterChip(
        selected = !isDefaultModeSelected,
        onClick = { onModeSelected.invoke(ViewType.MAP) },
        label = { Text(text = "Map") }
      )
      Spacer(modifier = Modifier.width(SmallPadding))
      FilterChip(
        selected = isDefaultModeSelected,
        onClick = { onModeSelected.invoke(ViewType.LIST) },
        label = { Text(text = "List") }
      )
    }
    Spacer(modifier = Modifier.height(SmallPadding))
    Text(
      color = MaterialTheme.colorScheme.tertiary,
      style = MaterialTheme.typography.bodyLarge,
      text = "Filters"
    )
    FilterChip(
      selected = isPrmFilterEnabled,
      onClick = onFilterClick,
      label = { Text(text = accessibilityFilterText) }
    )
  }
}
