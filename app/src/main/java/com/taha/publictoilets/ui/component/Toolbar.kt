package com.taha.publictoilets.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithTitle(
  title: String,
  actions: (() -> Unit)? = null,
  actionIcon: (@Composable () -> Unit)? = null
) = TopAppBar(
  title = {
    Text(
      text = title,
      style = MaterialTheme.typography.titleLarge,
      textAlign = TextAlign.Center
    )
  },
  actions = {
    IconButton(onClick = { actions?.invoke() }) {
      actionIcon?.invoke()
    }
  }
)

