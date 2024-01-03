package com.takuchan.milkypublisher.compose.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Date

@Composable
fun LogPreView(
    modifier: Modifier = Modifier.width(160.dp)
){
    Column(
        modifier = modifier
    ) {
        
    }
}
@Composable
fun LogCard(
    modifier: Modifier = Modifier.fillMaxWidth(),
    subjectText: String,
    detailText: String
){
    var data = Date()
    var simpleTimeFormat = java.text.SimpleDateFormat("HH:mm:ss")
    var time = simpleTimeFormat.format(data)
    Column(modifier = modifier){
        Text(text = "[$time] ",style = MaterialTheme.typography.labelMedium)
        Row(modifier = modifier){
            Text(modifier = Modifier.width(70.dp),
                text = subjectText,style = MaterialTheme.typography.titleMedium)
            Text(text = detailText,style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Preview
@Composable
fun PreviewLogPreView(){
    LogPreView()
    LogCard(modifier = Modifier.width(160.dp),subjectText = "Pose", detailText = "detail")
}