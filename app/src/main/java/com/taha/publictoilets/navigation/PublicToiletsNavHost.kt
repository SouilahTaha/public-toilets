package com.taha.publictoilets.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taha.publictoilets.ui.publictoilets.PublicToiletsScreen

@Composable
fun ProjectsNavHost(navController: NavHostController = rememberNavController()) =
    NavHost(
        navController = navController,
        startDestination = PublicToiletsDestinations.PUBLIC_TOILETS.path
    ) {
        composable(
            route = PublicToiletsDestinations.PUBLIC_TOILETS.path
        ) {
            PublicToiletsScreen()
        }
    }