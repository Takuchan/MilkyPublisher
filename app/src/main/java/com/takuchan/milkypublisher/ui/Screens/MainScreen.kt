package com.takuchan.milkypublisher.ui.Screens

import PublisherScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.takuchan.milkypublisher.R
import com.takuchan.milkypublisher.data.enums.MainScreenTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val nestedNavController = rememberNavController()
    val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()
    val currentTab = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.titleLarge
                        )
                        ConnectionStatusButton()
                    }
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                MainScreenTab.entries.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentTab == item.id,
                        onClick = { nestedNavController.navigate(item.id) }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(
                navController = nestedNavController,
                startDestination = "main/Program",
                modifier = Modifier,
            ) {
                composable("main/Program") {
                    ProgramListScreen()
                }
                composable("main/Publisher") {
                    PublisherScreen()
                }
                composable("main/Terminal") {
                    ProgrammingScreen()
                }
            }
        }
    }

}
@Composable
fun ConnectionStatusButton() {
    OutlinedButton(
        onClick = { /* ボタンクリック時の処理 */ },
        modifier = Modifier
            .padding(end = 8.dp)
            .widthIn(max = 150.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Warning Icon",
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Not connected".take(10),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}