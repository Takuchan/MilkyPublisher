package com.takuchan.milkypublisher.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.takuchan.milkypublisher.compose.utils.LogCard
import com.takuchan.milkypublisher.compose.utils.LogPreView
import com.takuchan.milkypublisher.viewmodel.LogViewModel

@Composable
fun LogScreen(
    logViewModel: LogViewModel,

){
    val paringName by logViewModel.logScreenList.observeAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        items(paringName){logData ->
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                LogCard(subjectText = logData.title, detailText = logData.subtitle, date = logData.date)

            }
            Divider()
        }
    }

}