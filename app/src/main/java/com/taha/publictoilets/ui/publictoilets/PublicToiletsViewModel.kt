package com.taha.publictoilets.ui.publictoilets

import PublicToiletsUiEvent
import PublicToiletsUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taha.domain.usecase.GetPublicToiletsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicToiletsViewModel @Inject constructor(
  private val getToiletsUseCase: GetPublicToiletsUseCase
) : ViewModel() {

  private val publicToiletsUiState = MutableStateFlow<PublicToiletsUiState>(PublicToiletsUiState.Loading)

  fun getPublicToiletsUiState(): StateFlow<PublicToiletsUiState> = publicToiletsUiState.asStateFlow()

  private val _uiEvent = Channel<PublicToiletsUiEvent>()
  val uiEvent = _uiEvent.receiveAsFlow()

  init {
    getToilets()
  }

  private fun getToilets() {
    viewModelScope.launch {
      val result: Result<List<Any>> = getToiletsUseCase()
      when (result.isSuccess) {
        true -> {
          publicToiletsUiState.value =
            PublicToiletsUiState.Success(toilets = result.getOrNull() ?: emptyList())

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

  /* fun changeView(viewType: ViewType) {
       val currentState = publicToiletsUiState as? PublicToiletsUiState.Success ?: throw Exception("")
       currentState.update { it.copy(viewType = viewType) }
   }*/
}