package com.taha.publictoilets.feature.toilet_details

import ToiletDetailsUiState
import androidx.lifecycle.SavedStateHandle
import com.taha.domain.usecase.GetToiletDetailsUseCase
import com.taha.publictoilets.mock.DefaultToiletEntityMock
import com.taha.publictoilets.navigation.NavigationConstants
import com.taha.publictoilets.uimodel.mapper.toPublicToiletUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ToiletDetailsViewModelTest {

  private lateinit var viewModel: ToiletDetailsViewModel
  private lateinit var getToiletDetailsUseCase: GetToiletDetailsUseCase
  private lateinit var savedStateHandle: SavedStateHandle
  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    getToiletDetailsUseCase = mockk()
    savedStateHandle = SavedStateHandle()
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `ViewModel should return success state with valid toiletId after initialization when getToiletDetailsUseCase returns success`() = runTest {
    val toiletId = "1"
    val toiletEntity = DefaultToiletEntityMock
    val expectedUiModel = toiletEntity.toPublicToiletUiModel()

    savedStateHandle[NavigationConstants.TOILET_ID_KEY] = toiletId
    coEvery { getToiletDetailsUseCase(toiletId) } returns Result.success(toiletEntity)

    viewModel = ToiletDetailsViewModel(getToiletDetailsUseCase, savedStateHandle)
    advanceUntilIdle()

    val uiState = viewModel.getToiletDetailsUiState().first()
    assertEquals(ToiletDetailsUiState.Success(toilet = expectedUiModel), uiState)
  }

  @Test
  fun `ViewModel should return error state when toiletId is null`() = runTest {
    savedStateHandle[NavigationConstants.TOILET_ID_KEY] = null

    viewModel = ToiletDetailsViewModel(getToiletDetailsUseCase, savedStateHandle)
    advanceUntilIdle()

    val uiState = viewModel.getToiletDetailsUiState().first()
    assertEquals(ToiletDetailsUiState.Error, uiState)
  }

  @Test
  fun `ViewModel should return failure state with valid toiletId after initialization when getToiletDetailsUseCase returns failure`() = runTest {
    val toiletId = "1"
    savedStateHandle[NavigationConstants.TOILET_ID_KEY] = toiletId
    coEvery { getToiletDetailsUseCase(toiletId) } returns Result.failure(Exception("API error"))

    viewModel = ToiletDetailsViewModel(getToiletDetailsUseCase, savedStateHandle)
    advanceUntilIdle()

    val uiState = viewModel.getToiletDetailsUiState().first()
    assertEquals(ToiletDetailsUiState.Error, uiState)
  }
}