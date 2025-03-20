package com.taha.domain.usecase

import com.taha.domain.mock.ToiletEntitiesMock
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
class GetPublicToiletsUseCaseTest {

  @MockK
  private lateinit var toiletRepository: PublicToiletsRepository

  @MockK
  private lateinit var locationRepository: LocationRepository

  private lateinit var useCase: GetPublicToiletsUseCase

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    useCase = GetPublicToiletsUseCase(toiletRepository, locationRepository)
  }

  @Test
  fun `GetPublicToiletsUseCase should return success with default parameters and updated user location when repository returns success`() =
    runTest {
      val userLocation = Pair(10.0, 20.0)
      val expectedEntities = ToiletEntitiesMock.map {
        it.copy(
          userLatitude = userLocation.first,
          userLongitude = userLocation.second
        )
      }
      coEvery { toiletRepository.getToilets(start = 0, rows = 10) } returns Result.success(
        ToiletEntitiesMock
      )
      coEvery { locationRepository.getUserLocation() } returns userLocation

      val result = useCase()

      assertTrue(result.isSuccess)
      assertEquals(expectedEntities, result.getOrNull())
    }

  @Test
  fun `GetPublicToiletsUseCase should return success with custom parameters and updated user location when repository returns success`() =
    runTest {
      val page = 2
      val pageSize = 40
      val userLocation = Pair(10.0, 20.0)
      val expectedEntities = ToiletEntitiesMock.map {
        it.copy(
          userLatitude = userLocation.first,
          userLongitude = userLocation.second
        )
      }
      coEvery { toiletRepository.getToilets(start = page, rows = pageSize) } returns Result.success(
        ToiletEntitiesMock
      )
      coEvery { locationRepository.getUserLocation() } returns userLocation
      val result = useCase(page = page, pageSize = pageSize)

      assertTrue(result.isSuccess)
      assertEquals(expectedEntities, result.getOrNull())
    }

  @Test
  fun `GetPublicToiletsUseCase should return result failure with exception when toiletRepository returns failure`() =
    runTest {
      val exception = Exception("Repository error")
      coEvery { toiletRepository.getToilets(start = 0, rows = 10) } returns Result.failure(exception)
      coEvery { locationRepository.getUserLocation() } returns null

      val result = useCase()

      assertTrue(result.isFailure)
      assertEquals(exception, result.exceptionOrNull())
    }

  @Test
  fun `GetPublicToiletsUseCase should return result failure when user location is not defined`() = runTest {
    val expected = ToiletEntitiesMock
    coEvery { toiletRepository.getToilets(start = 0, rows = 10) } returns Result.success(
      ToiletEntitiesMock
    )
    coEvery { locationRepository.getUserLocation() } returns null

    val result = useCase()

    assertEquals(expected, result.getOrNull())
  }
}