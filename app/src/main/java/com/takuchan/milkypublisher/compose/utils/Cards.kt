package com.takuchan.milkypublisher.compose.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluetoothSelectCard(
    name: String,
    address: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(60.dp).padding(5.dp)
    ) {
        Card(
            onClick = onClick
        ){
            Row(modifier = Modifier.fillMaxSize().padding(12.dp),){
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = name,
                    fontSize = 22.sp
                )
            }
        }

    }

}