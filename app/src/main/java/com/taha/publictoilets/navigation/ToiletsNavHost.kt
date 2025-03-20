package com.taha.publictoilets.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.taha.publictoilets.feature.public_toilets.ToiletsScreen
import com.taha.publictoilets.feature.toilet_details.ToiletDetailsScreen
import com.taha.publictoilets.navigation.NavigationConstants.Companion.TOILET_ID_KEY

@Composable
fun ProjectsNavHost(navController: NavHostController = rememberNavController()) =
  NavHost(
    navController = navController,
    startDestination = Screen.ToiletsScreen.route
  ) {
    composable(route = Screen.ToiletsScreen.route) {
      ToiletsScreen(
        onToiletClick = { toiletId ->
          navController.navigate(Screen.ToiletDetailsScreen.createRoute(toiletId))
        })
    }
    composable(
      route = Screen.ToiletDetailsScreen.route,
      arguments = listOf(navArgument(name = TOILET_ID_KEY) { type = NavType.StringType })
    ) {
      ToiletDetailsScreen(
        navigateToBack = { navController.popBackStack() }
      )
    }
  }