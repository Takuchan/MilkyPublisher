package com.takuchan.milkypublisher.data.enums

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.takuchan.milkypublisher.R

enum class MainScreenTab(
    val id:String,
    val icon:ImageVector,
    val label:String
) {
    Program(
        id = "main/Program",
        icon = Icons.Outlined.Home,
        label = "Program"

    ),
    Publisher(
        id = "main/Publisher",
        icon = Icons.Outlined.PlayArrow,
        label = "Publisher"
    ),
    Terminal(
        id = "main/Terminal",
        icon = Icons.Outlined.Build,
        label = "Terminal"
    )
}
