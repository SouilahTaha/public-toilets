package com.taha.publictoilets.feature.toilets.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.taha.designsystem.theme.LargePadding
import com.taha.designsystem.theme.NormalPadding

@Composable
fun EmptyListIllustration(
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(LargePadding),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = Icons.AutoMirrored.Filled.List,
      contentDescription = stringResource(com.taha.publictoilets.R.string.toilets_empty_list_illustration)
    )
    Spacer(modifier = Modifier.height(NormalPadding))
    Text(
      text = stringResource(com.taha.publictoilets.R.string.toilets_empty_list_message),
      style = MaterialTheme.typography.bodyMedium,
      textAlign = TextAlign.Center,
      color = MaterialTheme.colorScheme.outline
    )
  }
}

@Preview
@Composable
fun EmptyListIllustrationPreview() {
  MaterialTheme {
    EmptyListIllustration()
  }
}