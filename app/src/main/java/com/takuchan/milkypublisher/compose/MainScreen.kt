package com.takuchan.milkypublisher.compose

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.takuchan.milkypublisher.R
import com.takuchan.milkypublisher.compose.utils.LogPreView
import com.takuchan.milkypublisher.compose.utils.ReadyButton
import com.takuchan.milkypublisher.model.HomeBottomNavigationDataClass
import com.takuchan.milkypublisher.viewmodel.ConnectingViewModel
import com.takuchan.milkypublisher.viewmodel.DetectBluetoothList
import com.takuchan.milkypublisher.viewmodel.DetectState
import com.takuchan.milkypublisher.viewmodel.LogViewModel
import java.util.concurrent.ExecutorService

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    navMainController: NavController,
    modifier: Modifier = Modifier,
    detectState: DetectState,
    cameraExecutorService: ExecutorService,
    connectingViewModel: ConnectingViewModel,
    toBluetoothSettingButton: () -> Unit,
    logViewModel: LogViewModel
) {
    val navHomeScreenController = rememberNavController()
    val paringName by connectingViewModel.connectingStatus.observeAsState()
    val wifiIpAddr by connectingViewModel.wifiIpAddr.observeAsState("")
    val wifiPort by connectingViewModel.wifiPort.observeAsState("")



    val homeNavItems = listOf(
        HomeBottomNavigationDataClass(
            title = "Home",
            icon = Icons.Filled.Home,
            selectedIcon = Icons.Outlined.Home,
            state = true
        ),
        HomeBottomNavigationDataClass(
            title = "Controller",
            icon = ImageVector.vectorResource(id = R.drawable.gamepad_outlined),
            selectedIcon = ImageVector.vectorResource(id = R.drawable.gamepad_filled),
            state = false
        ),
        HomeBottomNavigationDataClass(
            title = "Config",
            icon = Icons.Filled.Settings,
            selectedIcon = Icons.Outlined.Settings,
            state = false
        ),
        HomeBottomNavigationDataClass(
            title = "Log",
            icon = ImageVector.vectorResource(R.drawable.sync_alt_filled),
            selectedIcon = ImageVector.vectorResource(R.drawable.sync_alt_outlined),
            state = false,
        )
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA,
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MilkyPublisher") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Share, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
            ) {
                NavigationBar {
                    homeNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navHomeScreenController.navigate(item.title)
                            },
                            label = {
                                Text(text = item.title)
                            },
                            alwaysShowLabel = false,
                            icon = {
                                BadgedBox(badge = {
                                    if (item.badgeCount != null) {
                                        Badge {
                                            Text(text = item.badgeCount.toString())
                                        }
                                    } else {
                                        Badge()
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.icon
                                        } else item.selectedIcon,
                                        contentDescription = item.title
                                    )
                                }
                            }

                        )
                    }
                }
            }
        }


    ) { padding ->
        Box(
            modifier = Modifier.padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (cameraPermissionState.status.isGranted) {
                    ReadyButton(modifier = Modifier, viewModel = detectState, onClick = {
                        detectState.setipv4Addr(wifiIpAddr)
                        detectState.setPort(wifiPort.toInt())
                        detectState.currentStateToggle()
                    })

                }
                NavHost(
                    navController = navHomeScreenController,
                    startDestination = homeNavItems[selectedItemIndex].title
                ) {
                    composable("Home") {

                        HomeScreen(
                            navMainController = navMainController,
                            navHomeController = navHomeScreenController,
                            detectState = detectState,
                            cameraExecutorService = cameraExecutorService,
                            toBluetoothSettingButton = {
                                navMainController.navigate("wifiSetting")
                            },
                            logViewModel = logViewModel
                        )
                    }
                    composable("Controller") {
                        RobotControllerScreen(
                        )
                    }
                    composable("Config"){
                        OthersPublisherScreen()
                    }
                    composable("Log"){
                        LogScreen(
                            logViewModel = logViewModel
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sync_alt_filled),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                )
                paringName?.let { Text(it+"で接続しています。", modifier = Modifier.padding(16.dp)) }

                IconButton(
                    modifier = Modifier.padding(0.dp),
                    onClick = { toBluetoothSettingButton() },
                ) {
                    Icon(
                        Icons.Filled.KeyboardArrowRight, contentDescription = null,
                    )
                }

            }
        }




    }
}
