package com.taha.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.taha.design_system.R
import com.taha.designsystem.theme.LargePadding
import com.taha.designsystem.theme.NormalPadding

@Composable
fun Error(onRetry: () -> Unit) =
  Column(
    modifier = Modifier
      .fillMaxSize()
      .clickable { onRetry.invoke() },
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      modifier = Modifier.padding(LargePadding),
      text = stringResource(R.string.error_retry),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.titleLarge,
    )
    Spacer(modifier = Modifier.padding(NormalPadding))
    Icon(
      imageVector = Icons.Filled.Refresh,
      contentDescription = stringResource(R.string.retry_icon_description),
      tint = MaterialTheme.colorScheme.primary
    )
  }

@Preview
@Composable
private fun ErrorPreview() = Error(onRetry = {})