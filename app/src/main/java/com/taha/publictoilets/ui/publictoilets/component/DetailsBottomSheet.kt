package com.taha.publictoilets.ui.publictoilets.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.taha.design_system.theme.LargePadding
import com.taha.publictoilets.uimodel.PublicToiletUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsBottomSheet(
  sheetState: SheetState,
  toilet: PublicToiletUiModel,
  onDismiss: () -> Unit
) = ModalBottomSheet(
  sheetState = sheetState,
  onDismissRequest = onDismiss,
) {
  Column(modifier = Modifier.padding(LargePadding)) {
    PublicToiletUiModelItem(toilet)
  }
}
