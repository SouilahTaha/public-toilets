package com.taha.domain.usecase

import com.taha.domain.mock.ToiletEntitiesMock
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
  private lateinit var repository: PublicToiletsRepository
  private lateinit var useCase: GetPublicToiletsUseCase

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    useCase = GetPublicToiletsUseCase(repository)
  }

  @Test
  fun `invoke with default parameters success`() = runTest {
    val expectedEntities = ToiletEntitiesMock
    coEvery { repository.getToilets(start = 0, rows = 10) } returns Result.success(expectedEntities)

    val result = useCase()

    assertTrue(result.isSuccess)
    assertEquals(expectedEntities, result.getOrNull())
  }

  @Test
  fun `invoke with custom parameters success`() = runTest {
    val page = 2
    val pageSize = 20
    coEvery { repository.getToilets(start = 40, rows = 20) } returns Result.success(ToiletEntitiesMock)

    val result = useCase(page = page, pageSize = pageSize)

    assertTrue(result.isSuccess)
    assertEquals(ToiletEntitiesMock, result.getOrNull())
  }

  @Test
  fun `invoke failure`() = runTest {
    val exception = Exception("Repository error")
    coEvery { repository.getToilets(start = 0, rows = 10) } returns Result.failure(exception)

    val result = useCase()

    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }
}