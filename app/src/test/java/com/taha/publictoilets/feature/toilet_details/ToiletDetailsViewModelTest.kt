package com.taha.publictoilets.feature.toilet_details

import ToiletDetailsUiState
import androidx.lifecycle.SavedStateHandle
import com.taha.domain.usecase.GetToiletDetailsUseCase
import com.taha.publictoilets.mock.DefaultToiletEntityMock
import com.taha.publictoilets.navigation.ToiletDetailsScreen
import com.taha.publictoilets.test_utils.mockkToRoute
import com.taha.publictoilets.uimodel.mapper.toPublicToiletUiModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ToiletDetailsViewModelTest {

  private lateinit var viewModel: ToiletDetailsViewModel
  private lateinit var getToiletDetailsUseCase: GetToiletDetailsUseCase
  private lateinit var savedStateHandle: SavedStateHandle
  private val testDispatcher = StandardTestDispatcher()
  private val toiletId = "1"
  private val toiletEntity = DefaultToiletEntityMock
  private val expectedUiModel = toiletEntity.toPublicToiletUiModel()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    getToiletDetailsUseCase = mockk()
    savedStateHandle = SavedStateHandle()
    savedStateHandle.mockkToRoute(ToiletDetailsScreen(toiletId = toiletId))
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `ViewModel should return success state with valid toiletId after initialization when getToiletDetailsUseCase returns success`() =
    runTest {
      coEvery { getToiletDetailsUseCase(toiletId) } returns Result.success(toiletEntity)
      viewModel = ToiletDetailsViewModel(getToiletDetailsUseCase, savedStateHandle)
      advanceUntilIdle()

      val uiState = viewModel.getToiletDetailsUiState().first()
      assertEquals(ToiletDetailsUiState.Success(toilet = expectedUiModel), uiState)
    }


  @Test
  fun `ViewModel should return failure state with valid toiletId after initialization when getToiletDetailsUseCase returns failure`() =
    runTest {
      coEvery { getToiletDetailsUseCase(toiletId) } returns Result.failure(Exception("API error"))
      viewModel = ToiletDetailsViewModel(getToiletDetailsUseCase, savedStateHandle)
      advanceUntilIdle()

      val uiState = viewModel.getToiletDetailsUiState().first()
      assertEquals(ToiletDetailsUiState.Error, uiState)
    }

  @Test
  fun `ViewModel should return error state when getToiletDetailsUseCase returns success but with error map`() =
    runTest {
      coEvery { getToiletDetailsUseCase(toiletId) } returns Result.failure(Exception("API error"))
      viewModel = ToiletDetailsViewModel(getToiletDetailsUseCase, savedStateHandle)
      advanceUntilIdle()

      val uiState = viewModel.getToiletDetailsUiState().first()
      assertEquals(ToiletDetailsUiState.Error, uiState)
    }

  @Test
  fun `ViewModel should return loading state at initialization`() = runTest {
    coEvery { getToiletDetailsUseCase(toiletId) } returns Result.success(toiletEntity)
    viewModel = ToiletDetailsViewModel(getToiletDetailsUseCase, savedStateHandle)

    val uiState = viewModel.getToiletDetailsUiState().first()
    assertTrue(uiState is ToiletDetailsUiState.Loading)
  }
}