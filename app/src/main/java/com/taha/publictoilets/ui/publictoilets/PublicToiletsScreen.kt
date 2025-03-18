package com.taha.publictoilets.ui.publictoilets

import PublicToiletsUiState
import ViewType
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.taha.design_system.theme.LargePadding
import com.taha.publictoilets.R
import com.taha.publictoilets.ui.component.Error
import com.taha.publictoilets.ui.component.Loader
import com.taha.publictoilets.ui.component.ToolbarWithTitle
import com.taha.publictoilets.ui.publictoilets.component.ConfigurationBottomSheet
import com.taha.publictoilets.ui.publictoilets.component.PublicToiletUiModelItem
import com.taha.publictoilets.uimodel.PublicToiletUiModel


@Composable
internal fun PublicToiletsScreen(
  viewModel: PublicToiletsViewModel = hiltViewModel<PublicToiletsViewModel>(),
) {
  val publicToiletUiState by viewModel.getPublicToiletsUiState().collectAsState()
  var isFilterSheetOpen by remember { mutableStateOf(false) }
  var isPrmFilterEnabled by remember { mutableStateOf(false) }

  Scaffold(
    modifier = Modifier.statusBarsPadding(),
    topBar = {
      ToolbarWithTitle(
        title = stringResource(R.string.public_toilets_title),
        actions = { isFilterSheetOpen = true },
        actionIcon = {
          Icon(
            imageVector = Icons.Filled.FilterList,
            contentDescription = "Filter"
          )
        }
      )
    },
    content = { innerPadding ->
      when (val currentState = publicToiletUiState) {
        is PublicToiletsUiState.Error -> Error()
        is PublicToiletsUiState.Loading -> Loader()
        is PublicToiletsUiState.Success -> PublicToiletsSuccessContent(
          isFilterSheetOpen = isFilterSheetOpen,
          isPrmFilterEnabled = isPrmFilterEnabled,
          isDefaultModeSelected = currentState.viewType == ViewType.LIST,
          publicToilets = currentState.toilets,
          modifier = Modifier.padding(innerPadding),
          onFilterClick = {
            isPrmFilterEnabled = !isPrmFilterEnabled
            //viewModel.filterPublicToiletsByPrm(isPrmFilterEnabled)
          },
          onDismiss = { isFilterSheetOpen = false },
          onModeSelected = { viewModel.changeView(it) }
        )
      }
    }
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PublicToiletsSuccessContent(
  isFilterSheetOpen: Boolean,
  isDefaultModeSelected: Boolean,
  isPrmFilterEnabled: Boolean,
  publicToilets: List<PublicToiletUiModel>,
  modifier: Modifier = Modifier,
  onFilterClick: () -> Unit,
  onDismiss: () -> Unit,
  onModeSelected: (ViewType) -> Unit,
) {
  val sheetState = rememberModalBottomSheetState()
  if (isFilterSheetOpen) {
    ConfigurationBottomSheet(
      sheetState = sheetState,
      isDefaultModeSelected = isDefaultModeSelected,
      isPrmFilterEnabled = isPrmFilterEnabled,
      onFilterClick = onFilterClick,
      onModeSelected = onModeSelected,
      onDismiss = onDismiss,
    )
  }
  Box(modifier = modifier) {
    if (isDefaultModeSelected) {
      PublicToiletListMode(toilets = publicToilets)
    } else {
      PublicToiletMapMode(toilets = publicToilets)
    }
  }
}

@Composable
private fun PublicToiletMapMode(toilets: List<PublicToiletUiModel>) {

}

@Composable
private fun PublicToiletListMode(toilets: List<PublicToiletUiModel>) =
  LazyColumn(contentPadding = PaddingValues(LargePadding)) {
    items(toilets) { toilet ->
      PublicToiletUiModelItem(toilet = toilet)
    }
  }


@Preview(showBackground = true)
@Composable
private fun PublicToiletsSuccessScreenPreview() {
  PublicToiletsScreen()
}