package com.takuchan.milkypublisher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takuchan.milkypublisher.preference.UDPController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetectState: ViewModel() {
    private val _currentState = MutableStateFlow<Boolean>(false)
    val currentState: StateFlow<Boolean> = _currentState

    private var ipv4Addr:String = ""
    private var port: Int = 0

    fun setipv4Addr(ip: String){
        ipv4Addr = ip
    }
    fun setPort(port: Int){
        this.port = port
    }


    fun currentStateToggle(){
        viewModelScope.launch {
            _currentState.value = !currentState.value
        }
        val udpController:UDPController = UDPController()
        udpController.setServerIp(ipv4Addr)
        udpController.setPort(port)
        udpController.startSending(currentState.value)
    }
}