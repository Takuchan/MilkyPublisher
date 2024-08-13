package com.takuchan.milkypublisher.ui.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.takuchan.milkypublisher.Entity.Welcome.RobotProgramEntry
import java.text.SimpleDateFormat

@Composable
fun RobotProgramEntryItem(entry: RobotProgramEntry,onDelete: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(entry.date))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text =entry.content)
            Button(onClick = onDelete) {
                Text("削除")
            }
        }
    }
}