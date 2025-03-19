package com.taha.publictoilets.ui.publictoilets

import android.location.Location
import com.taha.domain.usecase.GetPublicToiletsUseCase
import com.taha.publictoilets.extenstions.toLatLng
import com.taha.publictoilets.ui.publictoilets.mock.DefaultToiletEntityMock
import com.taha.publictoilets.ui.publictoilets.mock.NonAccessibleToiletEntityMock
import com.taha.publictoilets.ui.publictoilets.mock.ToiletEntitiesMock
import com.taha.publictoilets.uimodel.mapper.toToiletsUiModel
import io.mockk.coEvery
import io.mockk.every
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
class ToiletsViewModelTest {

  private lateinit var viewModel: ToiletsViewModel
  private lateinit var getToiletsUseCase: GetPublicToiletsUseCase
  private val testDispatcher = StandardTestDispatcher()

  @Before
  fun setup() {
    Dispatchers.setMain(testDispatcher)
    getToiletsUseCase = mockk()
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
  }

  @Test
  fun `viewModel should return success state after initialization when getToiletsUseCase is success`() = runTest {
    val toiletEntities = ToiletEntitiesMock
    val expectedUiModels = toiletEntities.toToiletsUiModel()
    coEvery { getToiletsUseCase() } returns Result.success(toiletEntities)

    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()


    val uiState = viewModel.getToiletsUiState().first()
    assertEquals(ToiletsUiState.Success(toilets = expectedUiModels, page = 1), uiState)
  }

  @Test
  fun `viewModel should return error state after initialization when getToiletsUseCase fails`() = runTest {
    val exception = Exception("API error")
    coEvery { getToiletsUseCase() } returns Result.failure(exception)

    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    val uiState = viewModel.getToiletsUiState().first()
    assertEquals(ToiletsUiState.Error, uiState)
  }

  @Test
  fun `loadMore should return success state with new list when getToiletsUseCase with param returns success`() = runTest {
    val initialToiletEntities = ToiletEntitiesMock
    val additionalToiletEntities = listOf(DefaultToiletEntityMock)
    val initialUiModels = initialToiletEntities.toToiletsUiModel()
    val additionalUiModels = additionalToiletEntities.toToiletsUiModel()

    coEvery { getToiletsUseCase() } returns Result.success(initialToiletEntities)
    coEvery { getToiletsUseCase(page = 1) } returns Result.success(additionalToiletEntities)
    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    viewModel.loadMore()
    advanceUntilIdle()

    val uiState = viewModel.getToiletsUiState().first()
    val expectedUiModels = initialUiModels + additionalUiModels
    assertEquals(ToiletsUiState.Success(toilets = expectedUiModels, page = 2), uiState)
  }

  @Test
  fun `loadMore should return success state with canPaginate false when getToiletsUseCase with param fails`() = runTest {
    val initialToiletEntities = ToiletEntitiesMock
    val exception = Exception("API error")
    coEvery { getToiletsUseCase() } returns Result.success(initialToiletEntities)
    coEvery { getToiletsUseCase(page = 1) } returns Result.failure(exception)
    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    viewModel.loadMore()
    advanceUntilIdle()

    val uiState = viewModel.getToiletsUiState().first() as ToiletsUiState.Success
    assertEquals(false, uiState.canPaginate)
  }

  @Test(expected = Exception::class)
  fun `loadMore with wrong ui state should throw error`() = runTest {
    val exception = Exception("API error")
    coEvery { getToiletsUseCase() } returns Result.failure(exception)
    coEvery { getToiletsUseCase(page = 1) } returns Result.failure(exception)

    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    viewModel.loadMore()
    advanceUntilIdle()
  }

  @Test
  fun `filterToilets with filter enabled should update state with filtered toilets`() = runTest {
    val expectedFilteredToilet = listOf(DefaultToiletEntityMock)
    coEvery { getToiletsUseCase() } returns Result.success(ToiletEntitiesMock)
    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    viewModel.filterToilets(isFilterEnabled = true)
    advanceUntilIdle()

    val uiState = viewModel.getToiletsUiState().first() as ToiletsUiState.Success
    val expectedUiModels = expectedFilteredToilet.toToiletsUiModel()
    assertEquals(expectedUiModels, uiState.toilets)
  }

  @Test
  fun `filterToilets with filter disabled should update state with all toilets`() = runTest {
    val toiletEntities = listOf(
      DefaultToiletEntityMock,
      NonAccessibleToiletEntityMock
    )

    coEvery { getToiletsUseCase() } returns Result.success(toiletEntities)
    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    viewModel.filterToilets(isFilterEnabled = false)
    advanceUntilIdle()

    val uiState = viewModel.getToiletsUiState().first() as ToiletsUiState.Success
    val expectedUiModels = toiletEntities.toToiletsUiModel()
    assertEquals(expectedUiModels, uiState.toilets)
  }

  @Test(expected = Exception::class)
  fun `filterToilets with wrong ui state should throw exception`() = runTest {
    val exception = Exception("API error")
    coEvery { getToiletsUseCase() } returns Result.failure(exception)
    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    viewModel.filterToilets(isFilterEnabled = true)
  }

  @Test
  fun `updateLocation should update state with user location`() = runTest {
    val toiletEntities = ToiletEntitiesMock
    val location = mockk<Location>()
    every { location.latitude } returns 48.8566
    every { location.longitude } returns 2.3522

    coEvery { getToiletsUseCase() } returns Result.success(toiletEntities)
    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    viewModel.updateLocation(location)
    advanceUntilIdle()

    val uiState = viewModel.getToiletsUiState().first() as ToiletsUiState.Success
    assertEquals(location.toLatLng(), uiState.userLocation)
  }

  @Test
  fun `updateLocation with wrong ui state should return Error ui state`() = runTest {
    val exception = Exception("API error")
    val location = mockk<Location>()
    every { location.latitude } returns 48.8566
    every { location.longitude } returns 2.3522
    coEvery { getToiletsUseCase() } returns Result.failure(exception)
    viewModel = ToiletsViewModel(getToiletsUseCase)
    advanceUntilIdle()

    viewModel.updateLocation(location)
    advanceUntilIdle()

    val uiState = viewModel.getToiletsUiState().first()
    assertEquals(ToiletsUiState.Error, uiState)
  }
}
