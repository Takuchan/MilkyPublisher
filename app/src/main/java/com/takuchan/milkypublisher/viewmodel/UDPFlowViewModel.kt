package com.takuchan.milkypublisher.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takuchan.milkypublisher.preference.UDPController
import com.takuchan.milkypublisher.repository.ReceiveUdpRepository


import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch



class UDPFlowViewModel(
    val receiveUdpRepository: ReceiveUdpRepository = ReceiveUdpRepository(UDPController())
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