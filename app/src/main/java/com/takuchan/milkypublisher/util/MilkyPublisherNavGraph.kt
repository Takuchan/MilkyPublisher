package com.takuchan.milkypublisher.data.navGraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.takuchan.milkypublisher.ui.Screens.MainScreen
import com.takuchan.milkypublisher.ui.Screens.ProgramListScreen

fun NavGraphBuilder.milkyPublisherNavGraph() {
    navigation(
        route = "main",
        startDestination = "main/entry"
    ) {
        composable("main/entry") {
            MainScreen()
        }
    }
}