package com.taha.publictoilets.ui.publictoilets

import PublicToiletsUiEvent
import PublicToiletsUiState
import ViewType
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taha.domain.entities.ToiletEntity
import com.taha.domain.usecase.GetPublicToiletsUseCase
import com.taha.publictoilets.extenstions.toLatLng
import com.taha.publictoilets.uimodel.PublicToiletUiModel
import com.taha.publictoilets.uimodel.mapper.toPublicToiletsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicToiletsViewModel @Inject constructor(
  private val getToiletsUseCase: GetPublicToiletsUseCase
) : ViewModel() {

  private var originalToiletsList = emptyList<PublicToiletUiModel>()
  private val publicToiletsUiState = MutableStateFlow<PublicToiletsUiState>(PublicToiletsUiState.Loading)
  internal fun getPublicToiletsUiState(): StateFlow<PublicToiletsUiState> = publicToiletsUiState.asStateFlow()

  private val publicToiletsUiEvent = Channel<PublicToiletsUiEvent>()
  internal fun getPublicToiletsUiEvent() = publicToiletsUiEvent.receiveAsFlow()

  init {
    getToilets()
  }

  internal fun getToilets() {
    viewModelScope.launch {
      val result: Result<List<ToiletEntity>> = getToiletsUseCase()
      if (result.isSuccess) {
        originalToiletsList = result.getOrNull()?.toPublicToiletsUiModel() ?: emptyList()
        publicToiletsUiState.value = PublicToiletsUiState.Success(toilets = originalToiletsList, page = 1)
      } else {
        publicToiletsUiState.value = PublicToiletsUiState.Error
      }
    }
  }

  internal fun loadMore() {
    val currentState = publicToiletsUiState.value as? PublicToiletsUiState.Success ?: throw Exception("state should be success")
    if (!currentState.canPaginate) return
    viewModelScope.launch {
      val result: Result<List<ToiletEntity>> = getToiletsUseCase(page = currentState.page)
      if (result.isSuccess) {
        originalToiletsList += result.getOrNull()?.toPublicToiletsUiModel() ?: emptyList()
        publicToiletsUiState.value = PublicToiletsUiState.Success(toilets = originalToiletsList, page = currentState.page + 1)
      } else {
        publicToiletsUiState.update { currentState.copy(canPaginate = false) }
      }
    }
  }

  internal fun filterPublicToilets(isPublicFilterEnabled: Boolean) {
    val currentState = publicToiletsUiState.value as? PublicToiletsUiState.Success ?: throw Exception("state should be success")
    val filteredList = if (isPublicFilterEnabled) {
      originalToiletsList.filter { it.isPrmAccessible }
    } else {
      originalToiletsList
    }
    publicToiletsUiState.update {
      currentState.copy(toilets = filteredList)
    }
  }

  internal fun updateLocation(location: Location) {
    val currentState = publicToiletsUiState.value as? PublicToiletsUiState.Success ?: return
    publicToiletsUiState.update { currentState.copy(userLocation = location.toLatLng()) }
  }

  internal fun changeView(viewType: ViewType) {
    val currentState = publicToiletsUiState.value as? PublicToiletsUiState.Success ?: throw Exception("state should be success")
    publicToiletsUiState.update {
      currentState.copy(viewType = viewType)
    }
  }
}