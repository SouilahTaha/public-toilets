package com.taha.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.taha.design_system.R
import com.taha.design_system.theme.ButtonCornerRadius
import com.taha.design_system.theme.SmallPadding
import com.taha.design_system.theme.VerySmallPadding

@Composable
fun ActionButton(
  onClick: () -> Unit,
  icon: ImageVector,
  textResId: Int,
  contentDescriptionResId: Int,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .wrapContentSize()
      .clip(RoundedCornerShape(ButtonCornerRadius))
      .background(MaterialTheme.colorScheme.primaryContainer)
      .clickable { onClick() }
      .padding(vertical = VerySmallPadding, horizontal = SmallPadding),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = icon,
      contentDescription = stringResource(contentDescriptionResId),
      tint = MaterialTheme.colorScheme.primary,
    )
    Spacer(modifier = Modifier.size(VerySmallPadding))
    Text(
      text = stringResource(textResId),
      color = MaterialTheme.colorScheme.primary,
      style = MaterialTheme.typography.bodyMedium,
    )
  }
}


@Preview
@Composable
fun ActionButtonPreview() {
  ActionButton(
    onClick = {},
    icon = Icons.Filled.Add,
    textResId = R.string.error_retry,
    contentDescriptionResId = R.string.error_retry
  )
}