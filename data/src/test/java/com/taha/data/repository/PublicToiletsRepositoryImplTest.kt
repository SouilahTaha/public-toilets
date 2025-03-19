package com.taha.data.repository

import com.taha.data.api.ApiService
import com.taha.data.mapper.toEntities
import com.taha.data.mapper.toEntity
import com.taha.data.mock.EmptyToiletResponseDtoMock
import com.taha.data.mock.ToiletDetailsResponseDtoMock
import com.taha.data.mock.ToiletResponseDtoMock
import com.taha.domain.entities.ToiletEntity
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
class PublicToiletsRepositoryImplTest {

  @MockK
  private lateinit var apiService: ApiService

  private lateinit var repository: PublicToiletsRepositoryImpl

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    repository = PublicToiletsRepositoryImpl(apiService)
  }

  @Test
  fun `getPublicToilets success`() = runTest {
    val start = 0
    val rows = 10

    val expectedEntities: List<ToiletEntity> = ToiletResponseDtoMock.records.toEntities()
    coEvery { apiService.getToilets(start = start, rows = rows) } returns ToiletResponseDtoMock


    val result = repository.getPublicToilets(start, rows)

    assertTrue(result.isSuccess)
    assertEquals(expectedEntities, result.getOrNull())
  }

  @Test
  fun `getPublicToilets failure`() = runTest {
    val start = 0
    val rows = 10
    val exception = Exception("API error")
    coEvery { apiService.getToilets(start = start, rows = rows) } throws exception


    val result = repository.getPublicToilets(start, rows)


    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }

  @Test
  fun `getToiletDetails success`() = runTest {
    val toiletId = "1"
    val expectedEntity = ToiletDetailsResponseDtoMock.records.first().toEntity()
    coEvery { apiService.getToiletDetails(toiletId = toiletId) } returns ToiletDetailsResponseDtoMock

    val result = repository.getToiletDetails(toiletId)

    assertTrue(result.isSuccess)
    assertEquals(expectedEntity, result.getOrNull())
  }

  @Test
  fun `getToiletDetails no record found`() = runTest {
    val toiletId = "1"
    coEvery { apiService.getToiletDetails(toiletId = toiletId) } returns EmptyToiletResponseDtoMock

    val result = repository.getToiletDetails(toiletId)

    assertTrue(result.isFailure)
    assertTrue(result.exceptionOrNull() is NoSuchElementException)
    assertEquals("No toilet record found for ID: $toiletId", result.exceptionOrNull()?.message)
  }

  @Test
  fun `getToiletDetails failure`() = runTest {
    val toiletId = "1"
    val exception = Exception("API error")
    coEvery { apiService.getToiletDetails(toiletId = toiletId) } throws exception

    val result = repository.getToiletDetails(toiletId)

    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }
}