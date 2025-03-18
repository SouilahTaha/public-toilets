package com.taha.publictoilets.ui.publictoilets

import PublicToiletsUiEvent
import PublicToiletsUiState
import ViewType
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taha.data.dto.Toilet
import com.taha.publictoilets.uimodel.PublicToiletUiModel
import com.taha.publictoilets.uimodel.publicToiletsUiModelMock
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
  //private val getToiletsUseCase: GetPublicToiletsUseCase
) : ViewModel() {

  private val publicToiletsUiState = MutableStateFlow<PublicToiletsUiState>(PublicToiletsUiState.Loading)
  internal fun getPublicToiletsUiState(): StateFlow<PublicToiletsUiState> = publicToiletsUiState.asStateFlow()

  private val publicToiletsUiEvent = Channel<PublicToiletsUiEvent>()
  internal fun getPublicToiletsUiEvent() = publicToiletsUiEvent.receiveAsFlow()

  init {
    getToilets()
  }

  private fun getToilets() {
    viewModelScope.launch {
      val result: Result<List<PublicToiletUiModel>> = Result.success(publicToiletsUiModelMock)//getToiletsUseCase()
      when (result.isSuccess) {
        true -> {
          publicToiletsUiState.value = PublicToiletsUiState.Success(toilets = result.getOrNull() ?: emptyList())
        }

        false -> {
          publicToiletsUiState.value = PublicToiletsUiState.Error
        }
      }
    }
  }



  /*
      fun updateLocation(latLng: LatLng) {
          currentState.update { it.copy(userLocation = latLng) }
      }
  */

  internal fun changeView(viewType: ViewType) {
    val currentState = publicToiletsUiState.value as? PublicToiletsUiState.Success ?: throw Exception("")
    publicToiletsUiState.update {
      currentState.copy(viewType = viewType)
    }
  }
}