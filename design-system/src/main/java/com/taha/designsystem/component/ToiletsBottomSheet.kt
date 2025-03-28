package com.taha.designsystem.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToiletsBottomSheet(
  sheetState: SheetState,
  onDismiss: () -> Unit,
  content: @Composable () -> Unit
) = ModalBottomSheet(
  sheetState = sheetState,
  onDismissRequest = onDismiss,
) { content.invoke() }