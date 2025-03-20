package com.taha.publictoilets.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
  @Serializable
  object ToiletsScreen

  @Serializable
  data class ToiletDetailsScreen(val toiletId: String)
}