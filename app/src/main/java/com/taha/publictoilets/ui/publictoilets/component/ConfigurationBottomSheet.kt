package com.taha.publictoilets.ui.publictoilets.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.taha.publictoilets.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ConfigurationBottomSheet(
  sheetState: SheetState,
  isPrmFilterEnabled: Boolean,
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
      text = "Filters"
    )
    FilterChip(
      selected = isPrmFilterEnabled,
      onClick = {
        onFilterClick.invoke()
        onDismiss.invoke()
      },
      label = { Text(text = accessibilityFilterText) }
    )
  }
}
