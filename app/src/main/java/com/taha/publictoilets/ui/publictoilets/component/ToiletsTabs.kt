package com.taha.publictoilets.ui.publictoilets.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.taha.design_system.theme.SmallPadding
import com.taha.publictoilets.ui.publictoilets.ViewType

@Composable
internal fun ToiletsTabs(
  isDefaultModeSelected: Boolean,
  modifier: Modifier = Modifier,
  onModeSelected: (ViewType) -> Unit
) {
  Row(modifier = modifier) {
    FilterChip(
      selected = !isDefaultModeSelected,
      onClick = {
        onModeSelected.invoke(ViewType.MAP)
      },
      label = { Text(stringResource(ViewType.MAP.stringRes)) }
    )
    Spacer(modifier = Modifier.width(SmallPadding))
    FilterChip(
      selected = isDefaultModeSelected,
      onClick = {
        onModeSelected.invoke(ViewType.LIST)
      },
      label = { Text(text = stringResource(ViewType.LIST.stringRes)) }
    )
  }
}


@Preview(name = "Default Mode")
@Composable
fun PublicToiletsTabsPreviewDefault() {
  MaterialTheme {
    Surface {
      ToiletsTabs(
        isDefaultModeSelected = true,
        onModeSelected = {}
      )
    }
  }
}

@Preview(name = "Map Mode")
@Composable
fun PublicToiletsTabsPreviewMap() {
  MaterialTheme {
    Surface {
      ToiletsTabs(
        isDefaultModeSelected = false,
        onModeSelected = {}
      )
    }
  }
}
