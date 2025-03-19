package com.taha.publictoilets.ui.publictoilets

import PublicToiletsUiState
import ViewType
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.taha.design_system.theme.LargePadding
import com.taha.design_system.theme.NormalPadding
import com.taha.publictoilets.R
import com.taha.publictoilets.extenstions.checkLocationPermission
import com.taha.publictoilets.extenstions.getCurrentLocation
import com.taha.publictoilets.ui.component.Error
import com.taha.publictoilets.ui.component.Loader
import com.taha.publictoilets.ui.component.ToolbarWithTitle
import com.taha.publictoilets.ui.publictoilets.component.ConfigurationBottomSheet
import com.taha.publictoilets.ui.publictoilets.component.EmptyListIllustration
import com.taha.publictoilets.ui.publictoilets.component.PublicToiletUiModelItem
import com.taha.publictoilets.ui.publictoilets.component.PublicToiletsMap
import com.taha.publictoilets.ui.publictoilets.component.PublicToiletsTabs
import com.taha.publictoilets.uimodel.PublicToiletUiModel
import kotlinx.coroutines.flow.distinctUntilChanged

private const val LOAD_MORE_THRESHOLD = 2

@Composable
internal fun PublicToiletsScreen(
  viewModel: PublicToiletsViewModel = hiltViewModel<PublicToiletsViewModel>(),
) {
  val context = LocalContext.current
  val publicToiletUiState by viewModel.getPublicToiletsUiState().collectAsState()
  var isFilterSheetOpen by remember { mutableStateOf(false) }
  var isPrmFilterEnabled by remember { mutableStateOf(false) }

  //handle location permission and map
  var hasPermission by remember { mutableStateOf(context.checkLocationPermission()) }
  val requestPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { isGranted: Boolean ->
      hasPermission = isGranted
      if (isGranted) {
        context.getCurrentLocation { location ->
          viewModel.updateLocation(location)
        }
      } else {
        Toast.makeText(
          /* context = */context,
          /* text = */context.getString(R.string.public_toilets_location_permission_denied_error_text),
          /* duration = */Toast.LENGTH_SHORT
        ).show()
      }
    }
  )

  LaunchedEffect(publicToiletUiState) {
    if (!hasPermission) {
      requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    } else {
      context.getCurrentLocation { location ->
        viewModel.updateLocation(location)
      }
    }
  }
//end handle location permission

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
        is PublicToiletsUiState.Loading -> Loader()
        is PublicToiletsUiState.Error -> Error(onRetry = viewModel::getToilets)
        is PublicToiletsUiState.Success -> PublicToiletsSuccessContent(
          isFilterSheetOpen = isFilterSheetOpen,
          isPrmFilterEnabled = isPrmFilterEnabled,
          isDefaultModeSelected = currentState.viewType == ViewType.LIST,
          publicToilets = currentState.toilets,
          userLocation = currentState.userLocation,
          modifier = Modifier.padding(innerPadding),
          onFilterClick = {
            isPrmFilterEnabled = !isPrmFilterEnabled
            viewModel.filterPublicToilets(isPrmFilterEnabled)
          },
          onDismiss = { isFilterSheetOpen = false },
          onModeSelected = viewModel::changeView,
          onLoadMore = viewModel::loadMore
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
  userLocation: LatLng?,
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
  onFilterClick: () -> Unit,
  onLoadMore: () -> Unit,
  onModeSelected: (ViewType) -> Unit,
) {
  val sheetState = rememberModalBottomSheetState()
  if (publicToilets.isEmpty()) {
    EmptyListIllustration()
  } else {
    PublicToilets(
      modifier = modifier,
      isDefaultModeSelected = isDefaultModeSelected,
      onModeSelected = onModeSelected,
      publicToilets = publicToilets,
      userLocation = userLocation,
      onLoadMore = onLoadMore
    )
  }
  if (isFilterSheetOpen) {
    ConfigurationBottomSheet(
      sheetState = sheetState,
      isPrmFilterEnabled = isPrmFilterEnabled,
      onFilterClick = onFilterClick,
      onDismiss = onDismiss,
    )
  }
}

@ExperimentalMaterial3Api
@Composable
private fun PublicToilets(
  modifier: Modifier,
  isDefaultModeSelected: Boolean,
  publicToilets: List<PublicToiletUiModel>,
  userLocation: LatLng?,
  onLoadMore: () -> Unit,
  onModeSelected: (ViewType) -> Unit
) {
  Column(modifier = modifier) {
    PublicToiletsTabs(
      isDefaultModeSelected = isDefaultModeSelected,
      onModeSelected = onModeSelected,
      modifier = Modifier.padding(start = LargePadding)
    )
    Spacer(modifier = Modifier.height(NormalPadding))
    if (isDefaultModeSelected) {
      PublicToiletListMode(toilets = publicToilets, onLoadMore = onLoadMore)
    } else {
      PublicToiletsMap(
        userLocation = userLocation,
        publicToilets = publicToilets
      )
    }
  }
}

@Composable
private fun PublicToiletListMode(
  toilets: List<PublicToiletUiModel>,
  onLoadMore: () -> Unit
) {
  val listState = rememberLazyListState()
  val loadMore = remember {
    derivedStateOf {
      val layoutInfo = listState.layoutInfo
      val totalItemsNumber = layoutInfo.totalItemsCount
      val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

      lastVisibleItemIndex > (totalItemsNumber - LOAD_MORE_THRESHOLD)
    }
  }
  LazyColumn(
    contentPadding = PaddingValues(LargePadding),
    state = listState
  ) {
    items(toilets) { toilet ->
      PublicToiletUiModelItem(toilet = toilet)
    }
  }

  LaunchedEffect(loadMore) {
    snapshotFlow { loadMore.value }
      .distinctUntilChanged()
      .collect {
        onLoadMore()
      }
  }

}

@Preview(showBackground = true)
@Composable
private fun PublicToiletsSuccessScreenPreview() = PublicToiletsScreen()