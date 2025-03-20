package com.taha.design_system.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarWithTitle(
  title: String,
  actions: (() -> Unit)? = null,
  navigation: (() -> Unit)? = null,
  navigationIcon: (@Composable () -> Unit)? = null,
  actionIcon: (@Composable () -> Unit)? = null
) = TopAppBar(
  navigationIcon = {
    navigation?.let {
      IconButton(onClick = { it.invoke() }) {
        navigationIcon?.invoke()
      }
    }
  },
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


@Preview
@Composable
fun ToolbarWithTitlePreview() = ToolbarWithTitle(
  title = "My App",
  navigation = {},
  navigationIcon = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") },
  actionIcon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
  actions = {}
)

@Preview
@Composable
fun ToolbarWithTitleNoActionsPreview() = ToolbarWithTitle(
  title = "No Actions",
  navigation = {},
  navigationIcon = { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") }
)
