package com.takuchan.milkypublisher.ViewModel.ProgramList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takuchan.milkypublisher.Entity.Welcome.RobotProgramEntry
import com.takuchan.milkypublisher.Repository.RobotProgram.RobotProgramRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgramListViewModel @Inject constructor(private val repository: RobotProgramRepository):ViewModel() {
    private val _entries = MutableStateFlow<List<RobotProgramEntry>>(emptyList())
    val entries: StateFlow<List<RobotProgramEntry>> = _entries

    init{
        viewModelScope.launch {
            repository.getAllEntries().collect{entries ->
                _entries.value = entries
            }
        }
    }

    fun addEntry(content:String){
        viewModelScope.launch {
            val entry = RobotProgramEntry(date = System.currentTimeMillis(), content = content)
            repository.insertEntry(entry)
        }
    }
    fun deleteEntry(entry:RobotProgramEntry){
        viewModelScope.launch {
            repository.deleteEntry(entry)
        }
    }
}