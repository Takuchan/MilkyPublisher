package com.takuchan.milkypublisher.ui.dataclasses

import androidx.compose.runtime.Composable

data class PublishItemData(
    val title: String,
    val detail: String,
    val icon: @Composable () -> Unit
)