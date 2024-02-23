package com.takuchan.milkypublisher.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun WifiSettingScreen(

){
    var wifiphrase1 by remember { mutableIntStateOf(0) }
    var wifiphrase2 by remember { mutableIntStateOf(0) }
    var wifiphrase3 by remember { mutableIntStateOf(0) }
    var wifiphrase4 by remember { mutableIntStateOf(0) }

}