package com.takuchan.milkypublisher.ui.Screens

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.takuchan.milkypublisher.ViewModel.publisherscreen.PublisherScreenViewModel
import com.takuchan.milkypublisher.data.model.publisherscreen.PublisherState
import com.takuchan.milkypublisher.ui.dataclasses.PublishItemData
import java.util.concurrent.Executors

@Composable
fun PublisherScreen(
    viewModel: PublisherScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var isExpanded by remember { mutableStateOf(false) }
    val transition = updateTransition(isExpanded, label = "expand_transition")
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val density = LocalDensity.current
    var boxSize by remember { mutableStateOf(PreviewScreenSize(0f,0f)) }
    val backgroundColor = MaterialTheme.colorScheme.background

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
            PublishItemGrid(viewModel)
        }
    }
}

@Composable
fun PublishItemGrid(
    viewModel: PublisherScreenViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(4) { index ->
            when (index) {
                0 -> PublishItem(
                    title = "Streaming",
                    detail = "データをトピックに載せます",
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                    isSelected = uiState.publisherDetectionState.isPublisher
                ) {
                    viewModel.setPublishState(!uiState.publisherDetectionState.isPublisher)
                }
                1 -> PublishItem(
                    title = "PoseDetection",
                    detail = "骨格検知のAIを実行します",
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                    isSelected = uiState.publisherDetectionState.isDetectPose
                ) {
                    viewModel.setPoseDetection(!uiState.publisherDetectionState.isDetectPose)
                }
                2 -> PublishItem(
                    title = "FaceDetection",
                    detail = "顔認識検知のAIを実行します",
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                    isSelected = uiState.publisherDetectionState.isDetectFace
                ) {
                    viewModel.setFaceDetection(!uiState.publisherDetectionState.isDetectFace)
                }
                3 -> PublishItem(
                    title = "FaceSmilingDetection",
                    detail = "笑顔になっているかどうかを検知します",
                    icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                    isSelected = uiState.publisherDetectionState.isDetectFaceSmiling
                ) {
                    viewModel.setFaceDetectionSmiling(!uiState.publisherDetectionState.isDetectFaceSmiling)
                }
            }
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


@Preview
@Composable
fun PublisherScreenPreview() {
    PublisherScreen()
}