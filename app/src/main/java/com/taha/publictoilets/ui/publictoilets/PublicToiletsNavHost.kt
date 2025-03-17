package com.taha.publictoilets.ui.publictoilets

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ProjectsNavHost(navController: NavHostController = rememberNavController()) =
    NavHost(
        navController = navController,
        startDestination = PublicToiletsDestinations.PROJECTS_LIST.path
    ) {
        composable(
            route = PublicToiletsDestinations.PROJECTS_LIST.path
        ) {
            ProjectsScreen()
        }
    }