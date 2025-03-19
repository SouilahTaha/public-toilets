import com.taha.publictoilets.uimodel.PublicToiletUiModel

sealed class PublicToiletDetailsUiState {
  data object Error : PublicToiletDetailsUiState()
  data object Loading : PublicToiletDetailsUiState()
  data class Success(val toilet: PublicToiletUiModel) : PublicToiletDetailsUiState()
}
