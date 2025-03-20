package com.taha.publictoilets.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taha.publictoilets.feature.public_toilets.ToiletsScreen
import com.taha.publictoilets.feature.toilet_details.ToiletDetailsScreen

@Composable
fun ProjectsNavHost(navController: NavHostController = rememberNavController()) =
  NavHost(
    navController = navController,
    startDestination = Screen.ToiletsScreen
  ) {
    composable<Screen.ToiletsScreen> {
      ToiletsScreen(
        onToiletClick = { toiletId ->
          navController.navigate(Screen.ToiletDetailsScreen(toiletId))
        })
    }
    composable<Screen.ToiletDetailsScreen> {
      ToiletDetailsScreen(
        navigateToBack = { navController.popBackStack() }
      )
    }
  }