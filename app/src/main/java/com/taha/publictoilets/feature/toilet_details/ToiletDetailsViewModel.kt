package com.taha.publictoilets.feature.toilet_details

import ToiletDetailsUiState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taha.domain.entities.ToiletEntity
import com.taha.domain.usecase.GetToiletDetailsUseCase
import com.taha.publictoilets.navigation.NavigationConstants.Companion.TOILET_ID_KEY
import com.taha.publictoilets.uimodel.mapper.toPublicToiletUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToiletDetailsViewModel @Inject constructor(
  private val getToiletDetailsUseCase: GetToiletDetailsUseCase,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  private val publicToiletDetailsUiState = MutableStateFlow<ToiletDetailsUiState>(ToiletDetailsUiState.Loading)
  internal fun getToiletDetailsUiState(): StateFlow<ToiletDetailsUiState> = publicToiletDetailsUiState.asStateFlow()

  init {
    savedStateHandle.getStateFlow<String?>(
      key = TOILET_ID_KEY,
      initialValue = null
    ).value?.run {
      getToilets(this)
    } ?: run {
      publicToiletDetailsUiState.value = ToiletDetailsUiState.Error
    }
  }

  private fun getToilets(toiletID: String) {
    viewModelScope.launch {
      val result: Result<ToiletEntity> = getToiletDetailsUseCase(toiletID)
      if (result.isSuccess) {
        val toiletDetails = result.map { entity ->
          entity.toPublicToiletUiModel()
        }.getOrElse {
          publicToiletDetailsUiState.value = ToiletDetailsUiState.Error
          return@launch
        }
        publicToiletDetailsUiState.value = ToiletDetailsUiState.Success(toilet = toiletDetails)
      } else {
        publicToiletDetailsUiState.value = ToiletDetailsUiState.Error
      }
    }
  }
}