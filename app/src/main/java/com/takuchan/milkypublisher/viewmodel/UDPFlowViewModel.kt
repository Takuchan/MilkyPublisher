package com.takuchan.milkypublisher.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.pose.Pose
import com.takuchan.milkypublisher.repository.ReceiveUdpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UDPFlowViewModel(
    private val receiveUdpRepository: ReceiveUdpRepository
):ViewModel() {
    init{
        viewModelScope.launch {
            receiveUdpRepository.receiveData
                .collect { data ->
                    _udpData.value = data
                }
        }
    }
}