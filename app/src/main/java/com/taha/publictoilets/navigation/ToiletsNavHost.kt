package com.taha.publictoilets.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taha.publictoilets.feature.details.ToiletDetailsScreen
import com.taha.publictoilets.feature.toilets.ToiletsScreen

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