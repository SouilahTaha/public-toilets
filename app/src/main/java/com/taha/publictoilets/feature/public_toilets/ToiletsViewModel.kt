package com.taha.publictoilets.feature.public_toilets

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taha.domain.entities.ToiletEntity
import com.taha.domain.usecase.GetPublicToiletsUseCase
import com.taha.publictoilets.extenstions.toLatLng
import com.taha.publictoilets.uimodel.ToiletUiModel
import com.taha.publictoilets.uimodel.mapper.toToiletsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val FIRST_TOILETS_PAGE = 1
private const val STATE_TYPE_EXCEPTION =  "state should be success"

@HiltViewModel
class ToiletsViewModel @Inject constructor(
  private val getToiletsUseCase: GetPublicToiletsUseCase
) : ViewModel() {

  private val toiletsUiState = MutableStateFlow<ToiletsUiState>(ToiletsUiState.Loading)
  internal fun getToiletsUiState(): StateFlow<ToiletsUiState> = toiletsUiState.asStateFlow()

  private val toiletsUiEvent = Channel<ToiletsUiEvent>()
  internal fun getToiletsUiEvent() = toiletsUiEvent.receiveAsFlow()

  private var originalToiletsList = emptyList<ToiletUiModel>()

  init {
    getToilets()
  }

  internal fun getToilets() {
    viewModelScope.launch {
      val result: Result<List<ToiletEntity>> = getToiletsUseCase()
      if (result.isSuccess) {
        originalToiletsList = result.getOrNull()?.toToiletsUiModel() ?: emptyList()
        toiletsUiState.value = ToiletsUiState.Success(toilets = originalToiletsList, page = FIRST_TOILETS_PAGE)
      } else {
        toiletsUiState.value = ToiletsUiState.Error
      }
    }
  }

  internal fun loadMore() {
    val currentState = toiletsUiState.value as? ToiletsUiState.Success ?: throw Exception(STATE_TYPE_EXCEPTION)
    if (!currentState.canPaginate) return
    viewModelScope.launch {
      val result: Result<List<ToiletEntity>> = getToiletsUseCase(page = currentState.page)
      if (result.isSuccess) {
        originalToiletsList += result.getOrNull()?.toToiletsUiModel() ?: emptyList()
        toiletsUiState.value = ToiletsUiState.Success(toilets = originalToiletsList, page = currentState.page + 1)
      } else {
        toiletsUiState.update { currentState.copy(canPaginate = false) }
      }
    }
  }

  internal fun filterToilets(isFilterEnabled: Boolean) {
    val currentState = toiletsUiState.value as? ToiletsUiState.Success ?: throw Exception(STATE_TYPE_EXCEPTION)
    val filteredList = if (isFilterEnabled) {
      originalToiletsList.filter { it.isPrmAccessible }
    } else {
      originalToiletsList
    }
    toiletsUiState.update {
      currentState.copy(toilets = filteredList)
    }
  }

  internal fun updateLocation(location: Location) {
    val currentState = toiletsUiState.value as? ToiletsUiState.Success ?: return
    toiletsUiState.update { currentState.copy(userLocation = location.toLatLng()) }
  }

  internal fun changeView(viewType: ViewType) {
    val currentState = toiletsUiState.value as? ToiletsUiState.Success ?: throw Exception(STATE_TYPE_EXCEPTION)
    toiletsUiState.update {
      currentState.copy(viewType = viewType)
    }
  }

  internal fun onToiletClick(toiletId: String) {
    viewModelScope.launch {
      toiletsUiEvent.send(ToiletsUiEvent.NavigateToToiletDetails(toiletId))
    }
  }
}