package com.takuchan.milkypublisher.repository

import android.util.Log
import com.takuchan.milkypublisher.preference.UDPController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ReceiveUdpRepository @Inject constructor(

    private val udpController: UDPController
) {
    fun getUDPData(): Flow<String> {
        return udpController.latestUDPData
    }
}