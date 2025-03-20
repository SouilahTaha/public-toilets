package com.taha.publictoilets.feature.details

import ToiletDetailsUiState
import androidx.lifecycle.SavedStateHandle
import com.taha.domain.usecase.GetToiletDetailsUseCase
import com.taha.publictoilets.mock.DefaultToiletEntityMock
import com.taha.publictoilets.navigation.Screen.ToiletDetailsScreen
import com.taha.publictoilets.uimodel.mapper.toPublicToiletUiModel
import com.taha.publictoilets.utils.mockkToRoute
import com.taha.publictoilets.utils.testCollect
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ToiletDetailsViewModelTest {

  @MockK
  private lateinit var getToiletDetailsUseCase: GetToiletDetailsUseCase

  private lateinit var savedStateHandle: SavedStateHandle
  private lateinit var viewModel: ToiletDetailsViewModel

  private val testDispatcher = StandardTestDispatcher()
  private val toiletId = "1"
  private val toiletEntity = DefaultToiletEntityMock
  private val expectedUiModel = toiletEntity.toPublicToiletUiModel()
  private val exception = Exception("API error")

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(testDispatcher)
    savedStateHandle = SavedStateHandle()
    savedStateHandle.mockkToRoute(ToiletDetailsScreen(toiletId = toiletId))
    viewModel = ToiletDetailsViewModel(getToiletDetailsUseCase, savedStateHandle)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `ViewModel should return loading state at initialization`() = runTest {
    coEvery { getToiletDetailsUseCase(toiletId) } returns Result.success(toiletEntity)

    val uiState = viewModel.getToiletDetailsUiState().first()

    assertTrue(uiState is ToiletDetailsUiState.Loading)
  }

  @Test
  fun `ViewModel should return success state with valid toiletId after initialization when getToiletDetailsUseCase returns success`() =
    runTest {
      coEvery { getToiletDetailsUseCase(toiletId) } returns Result.success(toiletEntity)
      val uiStates = viewModel.getToiletDetailsUiState().testCollect()

      assertEquals(
        ToiletDetailsUiState.Success(toilet = expectedUiModel),
        uiStates.last()
      )
    }

  @Test
  fun `ViewModel should return failure state with valid toiletId after initialization when getToiletDetailsUseCase returns failure`() =
    runTest {
      coEvery { getToiletDetailsUseCase(toiletId) } returns Result.failure(exception)
      val uiStates = viewModel.getToiletDetailsUiState().testCollect()
      assertEquals(ToiletDetailsUiState.Error, uiStates.last())
    }
}
