package com.taha.publictoilets.feature.toilets

import com.taha.domain.usecase.GetPublicToiletsUseCase
import com.taha.domain.usecase.SetUserLocationUseCase
import com.taha.publictoilets.mock.ToiletEntitiesMock
import com.taha.publictoilets.uimodel.ToiletUiModel
import com.taha.publictoilets.uimodel.mapper.toToiletsUiModel
import com.taha.publictoilets.utils.testCollect
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ToiletsViewModelTest {

  @MockK
  private lateinit var getToiletsUseCase: GetPublicToiletsUseCase

  @MockK
  private lateinit var setUserLocationUseCase: SetUserLocationUseCase

  private lateinit var viewModel: ToiletsViewModel

  private val testDispatcher = StandardTestDispatcher()
  private val expectedUiModels: List<ToiletUiModel> = ToiletEntitiesMock.toToiletsUiModel()
  private val exception = Exception("API error")
  private val firstPage = 1
  private val secondPage = 2
  private val testLocation = Pair(10.0, 20.0)
  private val toiletId = "1"

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(testDispatcher)
    viewModel = ToiletsViewModel(getToiletsUseCase, setUserLocationUseCase)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `getToiletsUiState should return loading state at initialization`() = runTest {
    coEvery { getToiletsUseCase() } returns Result.success(ToiletEntitiesMock)
    val uiState = viewModel.getToiletsUiState().first()

    assertTrue(uiState is ToiletsUiState.Loading)
  }

  @Test
  fun `getToiletsUiState should return success state with toilets after initialization when getToiletsUseCase returns success`() =
    runTest {
      coEvery { getToiletsUseCase() } returns Result.success(ToiletEntitiesMock)
      val uiStates = viewModel.getToiletsUiState().testCollect()

      assertEquals(ToiletsUiState.Success(expectedUiModels, firstPage), uiStates.last())
    }

  @Test
  fun `getToiletsUiState should return error state after initialization when getToiletsUseCase returns failure`() =
    runTest {
      coEvery { getToiletsUseCase() } returns Result.failure(exception)
      val uiStates = viewModel.getToiletsUiState().testCollect()

      assertEquals(ToiletsUiState.Error, uiStates.last())
    }

  @Test
  fun `loadMore should update state with next page when success`() = runTest {
    val expectedStates = listOf(
      ToiletsUiState.Loading,
      ToiletsUiState.Success(expectedUiModels, firstPage),
      ToiletsUiState.Success(expectedUiModels + expectedUiModels, secondPage)
    )

    coEvery { getToiletsUseCase() } returns Result.success(ToiletEntitiesMock)
    coEvery { getToiletsUseCase(page = firstPage) } returns Result.success(ToiletEntitiesMock)

    val uiStates = mutableListOf<ToiletsUiState>()
    val collectJob = launch {
      viewModel.getToiletsUiState().toList(uiStates)
    }
    advanceUntilIdle()

    viewModel.loadMore()
    advanceUntilIdle()
    assertEquals(expectedStates, uiStates)
    collectJob.cancel()
  }

  @Test
  fun `loadMore should update canPaginate to false when getToiletsUseCase fails`() = runTest {
    coEvery { getToiletsUseCase() } returns Result.success(ToiletEntitiesMock)
    coEvery { getToiletsUseCase(page = firstPage) } returns Result.failure(exception)
    val uiStates = mutableListOf<ToiletsUiState>()
    val collectJob = launch {
      viewModel.getToiletsUiState().toList(uiStates)
    }
    testScheduler.advanceUntilIdle()
    viewModel.loadMore()
    testScheduler.advanceUntilIdle()

    val lastState = (uiStates.last() as ToiletsUiState.Success)

    assertEquals(firstPage, lastState.page)
    assertTrue(!lastState.canPaginate)
    collectJob.cancel()
  }

  @Test
  fun `updateLocation should call setUserLocationUseCase with correct parameters`() = runTest {
    coEvery {
      setUserLocationUseCase(
        testLocation.first,
        testLocation.second
      )
    } returns Unit
    viewModel.updateLocation(testLocation.first, testLocation.second)
    advanceUntilIdle()

    coVerify { setUserLocationUseCase(testLocation.first, testLocation.second) }
  }

  @Test
  fun `changeView should update the viewType`() = runTest {
    coEvery { getToiletsUseCase() } returns Result.success(ToiletEntitiesMock)
    val uiStates = mutableListOf<ToiletsUiState>()
    val collectJob = launch {
      viewModel.getToiletsUiState().toList(uiStates)
    }

    advanceUntilIdle()
    viewModel.changeView(ViewType.MAP)
    advanceUntilIdle()

    val lastViewType = (uiStates.last() as ToiletsUiState.Success).viewType
    assertEquals(ViewType.MAP, lastViewType)
    collectJob.cancel()
  }

  @Test
  fun `onToiletClick should send NavigateToToiletDetails event`() = runTest {
    val expectedEvent = ToiletsUiEvent.NavigateToToiletDetails(toiletId)
    val eventFlow = viewModel.getToiletsUiEvent()
    viewModel.onToiletClick(toiletId)
    advanceUntilIdle()
    val event = eventFlow.first()
    assertEquals(expectedEvent, event)
  }
}