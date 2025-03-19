package com.taha.publictoilets.navigation

import com.taha.publictoilets.navigation.NavigationConstants.Companion.TOILETS_SCREEN_ROUTE
import com.taha.publictoilets.navigation.NavigationConstants.Companion.TOILET_DETAILS_SCREEN_ROUTE
import com.taha.publictoilets.navigation.NavigationConstants.Companion.TOILET_ID_KEY

sealed class Screen(val route: String) {
  data object ToiletsScreen : Screen(TOILETS_SCREEN_ROUTE)
  data object ToiletDetailsScreen : Screen("$TOILET_DETAILS_SCREEN_ROUTE/{$TOILET_ID_KEY}") {
    fun createRoute(toiletId: String) = "$TOILET_DETAILS_SCREEN_ROUTE/$toiletId"
  }
}