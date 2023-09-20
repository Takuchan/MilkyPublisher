package com.takuchan.milkypublisher.compose.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takuchan.milkypublisher.R
import com.takuchan.milkypublisher.viewmodel.DetectState


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ReadyButton(
    modifier: Modifier,
    viewModel: DetectState,
    onClick: () -> Unit
) {
    val nowDetection by viewModel.currentState.collectAsState()
    Button(
        onClick = onClick
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(nowDetection) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "Ready",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = modifier.padding(8.dp)
                )
            }else{
                Text(
                    text = "Now Streaming",
                    color = Color.White,
                    fontSize = 24.sp,
                    modifier = modifier.padding(8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_stop_24),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun ReadyButtonPreview(){
    ReadyButton(modifier = Modifier, viewModel = DetectState(), onClick = {})
}