package com.taha.publictoilets.extenstions

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocationExtensionsTest {

  @Before
  fun setup() {
    mockkStatic(Location::class)
  }

  @After
  fun tearDown() {
    unmockkAll()
  }

  @Test
  fun `toLatLng converts Location to LatLng correctly`() {
    val location = mockk<Location>()
    every { location.latitude } returns 48.8566
    every { location.longitude } returns 2.3522

    val latLng = location.toLatLng()

    assertEquals(48.8566, latLng.latitude, 0.0001)
    assertEquals(2.3522, latLng.longitude, 0.0001)
  }


  @Test
  fun `calculateDistance calculates distance correctly`() {
    val parisCoordinates = LatLng(48.8566, 2.3522)
    val newYorkCoordinates = LatLng(40.7128, -74.0060)
    val distanceInMeters = 5836000.0f

    every {
      Location.distanceBetween(
        parisCoordinates.latitude,
        parisCoordinates.longitude,
        newYorkCoordinates.latitude,
        newYorkCoordinates.longitude,
        any()
      )
    } answers {
      (lastArg() as FloatArray)[0] = distanceInMeters
    }

    val distance = parisCoordinates.calculateDistance(newYorkCoordinates)

    assertEquals(5836.0f, distance, 10f)
  }

  @Test
  fun `calculateDistance calculates zero distance for same location`() {
    // Given
    val latLng1 = LatLng(48.8566, 2.3522)
    val latLng2 = LatLng(48.8566, 2.3522)
    val distanceInMeters = 0.0f

    every {
      Location.distanceBetween(
        latLng1.latitude,
        latLng1.longitude,
        latLng2.latitude,
        latLng2.longitude,
        any()
      )
    } answers {
      (lastArg() as FloatArray)[0] = distanceInMeters
    }

    val distance = latLng1.calculateDistance(latLng2)

    assertEquals(0.0f, distance, 0.0001f)
  }

  @Test
  fun `calculateDistance calculates distance correctly with different locations`() {
    val sanFranciscoCoordinates = LatLng(37.7749, -122.4194)
    val losAngelesCoordinates = LatLng(34.0522, -118.2437)
    val distanceInMeters = 559000.0f
    val distanceInKilometers = 559.0f

    every {
      Location.distanceBetween(
        sanFranciscoCoordinates.latitude,
        sanFranciscoCoordinates.longitude,
        losAngelesCoordinates.latitude,
        losAngelesCoordinates.longitude,
        any()
      )
    } answers {
      (lastArg() as FloatArray)[0] = distanceInMeters
    }

    val distance = sanFranciscoCoordinates.calculateDistance(losAngelesCoordinates)

    assertEquals(distanceInKilometers, distance, 10f)
  }
}