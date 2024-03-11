package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobotControllerScreen(
    navHomeController: NavController,
    navMainController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Scaffold(
            topBar = { TopAppBar(title = { Text("ロボットコントローラ") }) }
        ) {padding ->
            Column(modifier = Modifier
                .padding(padding)
                .padding(12.dp)) {
                Text("接続先のノードを指定することで操作が可能です。")
                Text("Server: odom/geometry")
                Card(
                    colors = CardDefaults.cardColors(
                        contentColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column {
                        Text("進め")
                    }
                }

            }
            
        }
    }
}

