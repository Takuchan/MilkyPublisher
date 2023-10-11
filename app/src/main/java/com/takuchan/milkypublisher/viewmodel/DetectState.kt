package com.takuchan.milkypublisher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetectState: ViewModel() {
    private val _currentState = MutableStateFlow<Boolean>(false)
    val currentState: StateFlow<Boolean> = _currentState

    fun currentStateToggle(){
        viewModelScope.launch {
            _currentState.value = !currentState.value
        }
    }
}