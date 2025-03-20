package com.taha.data.mapper

import com.taha.data.mock.DefaultRecordDtoMock
import com.taha.data.mock.DefaultToiletEntityMock
import com.taha.data.mock.EmptyToiletResponseDtoMock
import com.taha.data.mock.ToiletEntitiesMock
import com.taha.data.mock.ToiletResponseDtoMock
import org.junit.Assert.assertEquals
import org.junit.Test

class RecordMapperTest {

  @Test
  fun `toEntity maps RecordDto to ToiletEntity correctly`() {
    val toiletEntity = DefaultRecordDtoMock.toEntity()
    assertEquals(DefaultToiletEntityMock, toiletEntity)
  }

  @Test
  fun `toEntities maps List of RecordDto to List of ToiletEntity correctly`() {
    val toiletEntities = ToiletResponseDtoMock.records.toEntities()
    assertEquals(ToiletEntitiesMock, toiletEntities)
  }

  @Test(expected = NoSuchElementException::class)
  fun `toEntity throws NoSuchElementException when geoPoint2d is empty`() {
    EmptyToiletResponseDtoMock.records.first().toEntity()
  }
}
