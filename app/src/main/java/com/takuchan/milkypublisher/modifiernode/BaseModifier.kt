package com.takuchan.milkypublisher.modifiernode

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.myBackground() = fillMaxSize()
fun Modifier.basePadding() = padding(16.dp)
fun Modifier.paddingSpaceUpandDown() = fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)
fun Modifier.paddingSpaceLeftRight() = fillMaxWidth().padding(horizontal = 16.dp)