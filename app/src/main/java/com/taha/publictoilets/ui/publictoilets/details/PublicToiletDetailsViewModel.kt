package com.taha.publictoilets.ui.publictoilets.details

import PublicToiletDetailsUiState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taha.domain.entities.ToiletEntity
import com.taha.domain.usecase.GetPublicToiletDetailsUseCase
import com.taha.publictoilets.uimodel.mapper.toPublicToiletUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublicToiletDetailsViewModel @Inject constructor(
  private val getPublicToiletDetailsUseCase: GetPublicToiletDetailsUseCase,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val publicToiletDetailsUiState = MutableStateFlow<PublicToiletDetailsUiState>(PublicToiletDetailsUiState.Loading)
  internal fun getPublicToiletDetailsUiState(): StateFlow<PublicToiletDetailsUiState> = publicToiletDetailsUiState.asStateFlow()

  init {
    savedStateHandle.getStateFlow("", "").value.run {
      getToilets(this)
    }
  }

  private fun getToilets(toiletID: String) {
    viewModelScope.launch {
      val result: Result<ToiletEntity> = getPublicToiletDetailsUseCase(toiletID)
      if (result.isSuccess) {
        val toiletDetails = result.map { entity ->
          entity.toPublicToiletUiModel()
        }.getOrElse {
          publicToiletDetailsUiState.value = PublicToiletDetailsUiState.Error
          return@launch
        }
        publicToiletDetailsUiState.value = PublicToiletDetailsUiState.Success(toilet = toiletDetails)
      } else {
        publicToiletDetailsUiState.value = PublicToiletDetailsUiState.Error
      }
    }
  }
}