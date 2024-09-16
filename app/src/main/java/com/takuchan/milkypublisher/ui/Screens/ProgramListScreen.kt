package com.takuchan.milkypublisher.ui.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.takuchan.milkypublisher.ViewModel.ProgramList.ProgramListViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.takuchan.milkypublisher.ui.Components.RobotProgramEntryItem

@Composable
fun ProgramListScreen(viewModel: ProgramListViewModel = hiltViewModel()) {
    val entries by viewModel.entries.collectAsState()
    var newEntryContent by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)){
        TextField(
            value = newEntryContent,
            onValueChange = { newEntryContent = it },
            label = { Text("New entry") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.addEntry(newEntryContent)
                newEntryContent = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Entry")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(entries) { entry ->
                RobotProgramEntryItem(entry, onDelete = { viewModel.deleteEntry(entry) })
            }
        }
    }

}