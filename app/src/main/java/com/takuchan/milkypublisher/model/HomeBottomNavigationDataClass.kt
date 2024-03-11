package com.takuchan.milkypublisher.model

import androidx.compose.ui.graphics.vector.ImageVector


data class HomeBottomNavigationDataClass (
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val state: Boolean,
    val badgeCount: Int? = null
)