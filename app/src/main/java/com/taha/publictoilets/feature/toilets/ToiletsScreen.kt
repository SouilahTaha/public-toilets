package com.taha.publictoilets.feature.toilets

import android.Manifest
import android.content.Context
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
import com.taha.designsystem.component.Error
import com.taha.designsystem.component.Loader
import com.taha.designsystem.component.ToolbarWithTitle
import com.taha.designsystem.theme.LargePadding
import com.taha.designsystem.theme.NormalPadding
import com.taha.publictoilets.NO_OPERATION
import com.taha.publictoilets.R
import com.taha.publictoilets.extenstions.checkLocationPermission
import com.taha.publictoilets.extenstions.getCurrentLocation
import com.taha.publictoilets.feature.toilets.ToiletsUiState.Error
import com.taha.publictoilets.feature.toilets.ToiletsUiState.Loading
import com.taha.publictoilets.feature.toilets.ToiletsUiState.Success
import com.taha.publictoilets.feature.toilets.component.ConfigurationBottomSheet
import com.taha.publictoilets.feature.toilets.component.EmptyListIllustration
import com.taha.publictoilets.feature.toilets.component.ToiletUiModelItem
import com.taha.publictoilets.feature.toilets.component.ToiletsMap
import com.taha.publictoilets.feature.toilets.component.ToiletsTabs
import com.taha.publictoilets.uimodel.ToiletUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged

private const val LOAD_MORE_THRESHOLD = 2
private const val DEFAULT_VISIBLE_ITEM_POSITION = 0


@Composable
internal fun ToiletsScreen(
  viewModel: ToiletsViewModel = hiltViewModel<ToiletsViewModel>(),
  onToiletClick: (String) -> Unit,
) {
  val context = LocalContext.current
  val toiletsUiState by viewModel.getToiletsUiState().collectAsState()
  var isFilterSheetOpen by remember { mutableStateOf(false) }
  var isPrmFilterEnabled by remember { mutableStateOf(false) }

  //handle events
  LaunchedEffect(key1 = Unit) {
    viewModel.getToiletsUiEvent().collectLatest { uiEvent ->
      handleToiletsUiEvent(uiEvent, onToiletClick)
    }
  }
  //end handle events

  //handle location permission
  var hasPermission by remember { mutableStateOf(context.checkLocationPermission()) }
  val requestPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission(),
    onResult = { isGranted: Boolean ->
      hasPermission = isGranted
      onPermission(context = context, isGranted = isGranted, viewModel = viewModel)
    }
  )

  LaunchedEffect(Unit) {
    if (!hasPermission) {
      requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    } else {
      context.getCurrentLocation { location ->
        viewModel.updateLocation(
          latitude = location.latitude,
          longitude = location.longitude
        )
      }
    }
  }
//end handle location permission

  Scaffold(
    modifier = Modifier.statusBarsPadding(),
    topBar = {
      ToolbarWithTitle(
        title = stringResource(R.string.toilets_title),
        actions = { isFilterSheetOpen = true },
        actionIcon = {
          Icon(
            imageVector = Icons.Filled.FilterList,
            contentDescription = stringResource(R.string.toilets_filter_description)
          )
        }
      )
    },
    content = { innerPadding ->
      when (val currentState = toiletsUiState) {
        is Loading -> Loader()
        is Error -> Error(onRetry = viewModel::getToilets)
        is Success -> ToiletsSuccessContent(
          isFilterSheetOpen = isFilterSheetOpen,
          isPrmFilterEnabled = isPrmFilterEnabled,
          isDefaultModeSelected = currentState.viewType == ViewType.LIST,
          toilets = currentState.toilets,
          userLocation = currentState.userLocation,
          modifier = Modifier.padding(innerPadding),
          onFilterClick = {
            isPrmFilterEnabled = !isPrmFilterEnabled
            viewModel.filterToilets(isPrmFilterEnabled)
          },
          onDismiss = { isFilterSheetOpen = false },
          onModeSelected = viewModel::changeView,
          onLoadMore = viewModel::loadMore,
          onToiletClick = viewModel::onToiletClick
        )
      }
    }
  )
}

private fun onPermission(
  context: Context,
  isGranted: Boolean,
  viewModel: ToiletsViewModel
) {
  if (isGranted) {
    context.getCurrentLocation { location ->
      viewModel.updateLocation(
        latitude = location.latitude,
        longitude = location.longitude
      )
    }
  } else {
    Toast.makeText(
      context,
      context.getString(R.string.toilets_location_permission_unavailable_error_text),
      Toast.LENGTH_SHORT
    ).show()
  }
}

private fun handleToiletsUiEvent(
  toiletsUiEvent: ToiletsUiEvent,
  onToiletClick: (String) -> Unit
) {
  if (toiletsUiEvent is ToiletsUiEvent.NavigateToToiletDetails) {
    onToiletClick(toiletsUiEvent.toiletId)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToiletsSuccessContent(
  isFilterSheetOpen: Boolean,
  isDefaultModeSelected: Boolean,
  isPrmFilterEnabled: Boolean,
  toilets: List<ToiletUiModel>,
  userLocation: LatLng?,
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit,
  onFilterClick: () -> Unit,
  onToiletClick: (String) -> Unit,
  onLoadMore: () -> Unit,
  onModeSelected: (ViewType) -> Unit,
) {
  val sheetState = rememberModalBottomSheetState()
  if (toilets.isEmpty()) {
    EmptyListIllustration()
  } else {
    Toilets(
      modifier = modifier,
      isDefaultModeSelected = isDefaultModeSelected,
      onModeSelected = onModeSelected,
      toilets = toilets,
      userLocation = userLocation,
      onLoadMore = onLoadMore,
      onToiletClick = onToiletClick
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
private fun Toilets(
  modifier: Modifier,
  isDefaultModeSelected: Boolean,
  toilets: List<ToiletUiModel>,
  userLocation: LatLng?,
  onToiletClick: (String) -> Unit,
  onLoadMore: () -> Unit,
  onModeSelected: (ViewType) -> Unit
) {
  Column(modifier = modifier) {
    ToiletsTabs(
      isDefaultModeSelected = isDefaultModeSelected,
      onModeSelected = onModeSelected,
      modifier = Modifier.padding(start = LargePadding)
    )
    Spacer(modifier = Modifier.height(NormalPadding))
    if (isDefaultModeSelected) {
      ToiletListMode(
        toilets = toilets,
        onLoadMore = onLoadMore,
        onToiletClick = onToiletClick
      )
    } else {
      ToiletsMap(
        userLocation = userLocation,
        publicToilets = toilets,
        onToiletClick = onToiletClick
      )
    }
  }
}

@Composable
private fun ToiletListMode(
  toilets: List<ToiletUiModel>,
  onLoadMore: () -> Unit,
  onToiletClick: (String) -> Unit
) {
  val listState = rememberLazyListState()
  val loadMore = remember {
    derivedStateOf {
      val layoutInfo = listState.layoutInfo
      val totalItemsNumber = layoutInfo.totalItemsCount
      val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: DEFAULT_VISIBLE_ITEM_POSITION) + 1

      lastVisibleItemIndex > (totalItemsNumber - LOAD_MORE_THRESHOLD)
    }
  }
  LazyColumn(
    contentPadding = PaddingValues(LargePadding),
    state = listState
  ) {
    items(toilets) { toilet ->
      ToiletUiModelItem(
        toilet = toilet,
        onToiletClick = onToiletClick
      )
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
private fun ToiletsSuccessScreenPreview() = ToiletsScreen(onToiletClick = { NO_OPERATION })