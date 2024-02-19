package com.takuchan.milkypublisher.repository

import android.util.Log
import com.takuchan.milkypublisher.preference.UDPController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ReceiveUdpRepository(
    private val udpController: UDPController
) {
    val receiveData: Flow<String> =
        udpController.latestUDPData
            .onEach { data -> Log.d("getUDP",data) }
}