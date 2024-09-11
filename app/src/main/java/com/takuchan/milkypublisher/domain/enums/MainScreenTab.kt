package com.takuchan.milkypublisher.data.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

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
        icon = Icons.Outlined.Home,
        label = "Publisher"
    ),
    Terminal(
        id = "main/Terminal",
        icon = Icons.Outlined.Home,
        label = "Terminal"
    )
}
