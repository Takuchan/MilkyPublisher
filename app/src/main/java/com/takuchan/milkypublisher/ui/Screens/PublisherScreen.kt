package com.takuchan.milkypublisher.ui.Screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.takuchan.milkypublisher.ui.Screens.CameraPreview
import com.takuchan.milkypublisher.ui.Screens.PreviewScreenSize
import com.takuchan.milkypublisher.ui.dataclasses.PublishItemData
import java.util.concurrent.Executors

@Composable
fun PublisherScreen() {
    var isExpanded by remember { mutableStateOf(false) }
    val transition = updateTransition(isExpanded, label = "expand_transition")
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val density = LocalDensity.current
    var boxSize by remember { mutableStateOf(PreviewScreenSize(0f,0f)) }
    val backgroundColor = MaterialTheme.colorScheme.background
    val items = listOf(
        PublishItemData("Item 1", "Detail 1") { Icon(Icons.Default.Add, contentDescription = "Add") },
        PublishItemData("Item 2", "Detail 2") { Icon(Icons.Default.Add, contentDescription = "Add") },
        PublishItemData("Item 2", "Detail 2") { Icon(Icons.Default.Add, contentDescription = "Add") },
        PublishItemData("Item 2", "Detail 2") { Icon(Icons.Default.Add, contentDescription = "Add") },
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(transition.animateFloat(label = "weight") { if (it) 1f else 0.75f }.value)
                .padding(16.dp)
                .background(Color.LightGray, RoundedCornerShape(30.dp))
                .onGloballyPositioned { coordinates ->
                    boxSize = PreviewScreenSize(
                        coordinates.size.width / density.density,
                        coordinates.size.height / density.density
                    )
                }
        ) {

            CameraPreview(executor = cameraExecutor, screenSize = boxSize)
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .background(backgroundColor)
            ) {
                Icon(
                    tint = getAdaptiveIconColor(backgroundColor = backgroundColor),
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Shrink" else "Expand"
                )
            }
        }

        AnimatedVisibility(
            visible = !isExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            PublishItemGrid(items)
        }
    }
}

@Composable
fun getAdaptiveIconColor(backgroundColor: Color): Color {
    val luminance = backgroundColor.luminance()
    return if (luminance > 0.5f) {
        Color.Black
    } else {
        Color.White
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublishItem(
    title: String,
    detail: String,
    icon: @Composable () -> Unit,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icon()
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

    @Composable
    fun PublishItemGrid(items: List<PublishItemData>) {
        var selectedItem by remember { mutableStateOf<PublishItemData?>(null) }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                PublishItem(
                    title = item.title,
                    detail = item.detail,
                    icon = item.icon,
                    isSelected = item == selectedItem,
                    onItemClick = { selectedItem = item }
                )
            }
        }

}
@Preview
@Composable
fun PublisherScreenPreview() {
    PublisherScreen()
}