package com.taha.domain.usecase

import com.taha.domain.entities.ToiletEntity
import com.taha.domain.mock.DefaultToiletEntityMock
import com.taha.domain.mock.NonAccessibleToiletEntityMock
import com.taha.domain.repository.LocationRepository
import com.taha.domain.repository.PublicToiletsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetToiletDetailsUseCaseTest {

  @MockK
  private lateinit var toiletsRepository: PublicToiletsRepository

  @MockK
  private lateinit var locationRepository: LocationRepository

  private lateinit var useCase: GetToiletDetailsUseCase

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    useCase = GetToiletDetailsUseCase(toiletsRepository, locationRepository)
  }

  @Test
  fun `GetToiletDetailsUseCase should return success with updated user location when repository returns success`() =
    runTest {
      val toiletId = "1"
      val userLocation = Pair(10.0, 20.0)
      val expectedEntity = DefaultToiletEntityMock.copy(userLatitude = userLocation.first, userLongitude = userLocation.second)

      coEvery { toiletsRepository.getToiletDetails(toiletId) } returns Result.success(
        DefaultToiletEntityMock
      )
      coEvery { locationRepository.getUserLocation() } returns userLocation

      val result = useCase(toiletId)

      assertTrue(result.isSuccess)
      assertEquals(expectedEntity, result.getOrNull())
    }

  @Test
  fun `GetToiletDetailsUseCase should return failure with exception if toiletsRepository fails`() =
    runTest {
      val toiletId = "1"
      val exception = Exception("Repository error")
      coEvery { toiletsRepository.getToiletDetails(toiletId) } returns Result.failure(exception)
      coEvery { locationRepository.getUserLocation() } returns null

      val result = useCase(toiletId)

      assertTrue(result.isFailure)
      assertEquals(exception, result.exceptionOrNull())
    }

  @Test
  fun `GetToiletDetailsUseCase should return success with updated user location when different id is requested`() =
    runTest {
      val toiletId = "2"
      val userLocation = Pair(10.0, 20.0)
      val expectedEntity: ToiletEntity =
        NonAccessibleToiletEntityMock.copy(userLatitude = userLocation.first, userLongitude = userLocation.second)
      coEvery { toiletsRepository.getToiletDetails(toiletId) } returns Result.success(
        NonAccessibleToiletEntityMock
      )
      coEvery { locationRepository.getUserLocation() } returns userLocation

      val result = useCase(toiletId)

      assertTrue(result.isSuccess)
      assertEquals(expectedEntity, result.getOrNull())
    }

  @Test
  fun `GetToiletDetailsUseCase should return success when user location is not defined`() = runTest {
    val toiletId = "2"
    val expectedResult = NonAccessibleToiletEntityMock
    coEvery { toiletsRepository.getToiletDetails(toiletId) } returns Result.success(
      NonAccessibleToiletEntityMock
    )
    coEvery { locationRepository.getUserLocation() } returns null

    val result = useCase(toiletId)

    assertEquals(expectedResult, result.getOrNull())
  }
}