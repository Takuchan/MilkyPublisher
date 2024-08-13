package com.takuchan.milkypublisher.ui.Components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.takuchan.milkypublisher.data.navGraph.milkyPublisherNavGraph
import com.takuchan.milkypublisher.ui.Screens.ProgramListScreen

@Composable
fun MilkyPublisherNavHost(
    navcontroller: NavHostController = rememberNavController(),
    startDestination: String = "main"
){
    NavHost(navController = navcontroller,startDestination = startDestination){
        milkyPublisherNavGraph()
    }
}

