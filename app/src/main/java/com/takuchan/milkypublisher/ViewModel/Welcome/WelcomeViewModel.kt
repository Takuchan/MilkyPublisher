package com.takuchan.milkypublisher.ViewModel.Welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takuchan.milkypublisher.Repository.Welcome.WelcomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WelcomeViewModel(private val repository: WelcomeRepository): ViewModel() {
    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

    init{
        viewModelScope.launch {
            repository.isFirstLaunch.collect{ isFirst ->
                _isFirstLaunch.value = isFirst
            }
        }
    }

    fun onWelcomeComplete(){
        viewModelScope.launch {
            repository.setFirstLaunchComplete()
        }
    }
}