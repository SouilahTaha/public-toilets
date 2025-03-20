@file:OptIn(ExperimentalMaterial3Api::class)

package com.taha.publictoilets.feature.toilets.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.taha.designsystem.component.ToiletsBottomSheet
import com.taha.designsystem.theme.LargePadding
import com.taha.publictoilets.NO_OPERATION
import com.taha.publictoilets.uimodel.ToiletUiModel
import com.taha.publictoilets.uimodel.defaultToiletMock

@Composable
internal fun DetailsBottomSheet(
  sheetState: SheetState,
  toilet: ToiletUiModel,
  onDismiss: () -> Unit,
  onToiletClick: (String) -> Unit,
) = ToiletsBottomSheet(
  sheetState = sheetState,
  onDismiss = onDismiss,
) {
  DetailsContent(toilet, onToiletClick)
}

@Composable
private fun DetailsContent(
  toilet: ToiletUiModel,
  onToiletClick: (String) -> Unit
) {
  Column(modifier = Modifier.padding(LargePadding)) {
    ToiletUiModelItem(
      toilet = toilet,
      onToiletClick = onToiletClick
    )
  }
}

@Preview
@Composable
fun DetailsContentPreview() {
  DetailsContent(
    toilet = defaultToiletMock,
    onToiletClick = { NO_OPERATION }
  )
}