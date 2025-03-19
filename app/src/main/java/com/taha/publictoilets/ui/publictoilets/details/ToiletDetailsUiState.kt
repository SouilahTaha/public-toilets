import com.taha.publictoilets.uimodel.ToiletUiModel

sealed class ToiletDetailsUiState {
  data object Error : ToiletDetailsUiState()
  data object Loading : ToiletDetailsUiState()
  data class Success(val toilet: ToiletUiModel) : ToiletDetailsUiState()
}
