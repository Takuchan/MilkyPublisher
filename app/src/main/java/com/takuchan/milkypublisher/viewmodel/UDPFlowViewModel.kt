package com.takuchan.milkypublisher.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takuchan.milkypublisher.repository.ReceiveUdpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UDPFlowViewModel @Inject constructor(
    val receiveUdpRepository: ReceiveUdpRepository
) : ViewModel() {
    val _getStr: MutableLiveData<String> = MutableLiveData<String>("")
    val receiveUDP: MutableLiveData<String> = _getStr

    init {
        viewModelScope.launch {
            receiveUdpRepository.getUDPData()
                .catch { exception -> Log.d("error", exception.message.toString()) }
                .collect { data ->
                    Log.d("UDPFlowViewModel", data)
                    _getStr.value = data
                }
        }
    }
}