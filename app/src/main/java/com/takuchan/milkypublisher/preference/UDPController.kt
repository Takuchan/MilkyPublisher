package com.takuchan.milkypublisher.preference

import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.mlkit.vision.pose.Pose
import com.takuchan.milkypublisher.viewmodel.ControllerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.DatagramSocket
import java.net.DatagramPacket
import javax.inject.Inject
class UDPController @Inject constructor (
    private val controllerViewModel: ControllerViewModel
) {


    init {
        controllerViewModel._controllerModel.observeForever{
            Log.d("受けっとった",it.toString())
        }
    }

    var ip = InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(), 227.toByte(), 184.toByte()))
    var port = 4001
    val latestUDPData: Flow<String> = flow {

        val socket = DatagramSocket(port)
        val buffer = ByteArray(8192)
        while (true) {
            val packet = DatagramPacket(buffer, buffer.size)
            withContext(Dispatchers.IO) {
                socket.receive(packet)
            }
                val result =  String(packet.data, 0, packet.length)
                Log.d("getUDP", result)

                emit(result)
        }
    }


    fun send(data: Pose){
        val socket = DatagramSocket()
        val buffer = data.toString().toByteArray()
        for (item in data.allPoseLandmarks){
            val landmarkName = item.landmarkType
            val landmarkPoint = item.position
            val sendData:String = "$landmarkPoint%$landmarkName";
//            val sendData: String = "$landmarkName"
            val packet = DatagramPacket(sendData.toByteArray(), sendData.toByteArray().size, ip, port)
            socket.send(packet)
        }
        socket.close()
    }
}