package com.taha.domain.usecase

import com.taha.domain.entities.ToiletEntity
import com.taha.domain.mock.DefaultToiletEntityMock
import com.taha.domain.mock.NonAccessibleToiletEntityMock
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
  private lateinit var repository: PublicToiletsRepository
  private lateinit var useCase: GetToiletDetailsUseCase

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    useCase = GetToiletDetailsUseCase(repository)
  }

  @Test
  fun `GetToiletDetailsUseCase success if repository returns success`() = runTest {
    val toiletId = "1"
    coEvery { repository.getToiletDetails(toiletId) } returns Result.success(DefaultToiletEntityMock)

    val result = useCase(toiletId)

    assertTrue(result.isSuccess)
    assertEquals(DefaultToiletEntityMock, result.getOrNull())
  }

  @Test
  fun `GetToiletDetailsUseCase should return failure with exception if repository fails`() = runTest {
    val toiletId = "1"
    val exception = Exception("Repository error")
    coEvery { repository.getToiletDetails(toiletId) } returns Result.failure(exception)

    val result = useCase(toiletId)

    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }

  @Test
  fun `GetToiletDetailsUseCase with different id should return success when repository returns success`() = runTest {
    val toiletId = "2"
    val expectedEntity: ToiletEntity = NonAccessibleToiletEntityMock
    coEvery { repository.getToiletDetails(toiletId) } returns Result.success(expectedEntity)

    val result = useCase(toiletId)

    assertTrue(result.isSuccess)
    assertEquals(expectedEntity, result.getOrNull())
  }
}