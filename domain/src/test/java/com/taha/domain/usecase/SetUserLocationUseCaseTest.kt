package com.taha.domain.usecase

import com.taha.domain.repository.LocationRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SetUserLocationUseCaseTest {

  @MockK
  private lateinit var locationRepository: LocationRepository

  private lateinit var useCase: SetUserLocationUseCase

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    useCase = SetUserLocationUseCase(locationRepository)
  }

  @Test
  fun `SetUserLocationUseCase should call locationRepository updateUserLocation with correct parameters`() =
    runTest {
      val latitude = 37.7749
      val longitude = -122.4194
      coEvery { locationRepository.updateUserLocation(lat = latitude, long = longitude) } returns Unit

      useCase(latitude, longitude)

      coVerify { locationRepository.updateUserLocation(lat = latitude, long = longitude) }
    }
}