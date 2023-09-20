package com.takuchan.milkypublisher

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.takuchan.milkypublisher.compose.HomeScreen
import com.takuchan.milkypublisher.ui.theme.MilkyPublisherTheme
import com.takuchan.milkypublisher.viewmodel.DetectState
class MainActivity : ComponentActivity() {

    val viewModel: DetectState by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MilkyPublisherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MilkyPublisherApp(detectStateViewModel = viewModel)
                }
            }
        }
    }

}

@Composable
fun MilkyPublisherApp(detectStateViewModel: DetectState){
    val navController = rememberNavController()
    MilkyPublisherNavHost(navController = navController, detectStateViewModel = detectStateViewModel)
}

@Composable
fun MilkyPublisherNavHost(
    navController: NavHostController,
    detectStateViewModel: DetectState
){

    NavHost(navController = navController, startDestination = "home"){
        composable("home") {
            HomeScreen(navController, detectState = detectStateViewModel)
        }
    }
}