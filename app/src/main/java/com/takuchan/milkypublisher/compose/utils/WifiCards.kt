package com.takuchan.milkypublisher.compose.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.takuchan.milkypublisher.modifiernode.paddingSpaceLeftRight

@Composable
fun wifiListCard(
    wifiname: String,
    wifiIP: String,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        Text(text = wifiname, style = MaterialTheme.typography.titleMedium)
        Text(text = wifiIP, style = MaterialTheme.typography.labelMedium)
    }
}
@Preview
@Composable
fun PreviewWifiListCard() {
    wifiListCard("wifi","192.168",modifier = Modifier.paddingSpaceLeftRight())
}