package com.takuchan.milkypublisher.preference

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.mlkit.vision.pose.Pose
import com.takuchan.milkypublisher.model.PoseLandmarkDataclass
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


object TmpUDPData{
    var landmarkListData = mutableListOf<PoseLandmarkDataclass>()

    fun putLandmarkListData(data: MutableList<PoseLandmarkDataclass>){
        landmarkListData = data
    }
}

class UDPController(
) {


    var ip = InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(), 0.toByte(), 199.toByte()))
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
                emit(result)
        }
    }
    
    fun sendPose(data: Pose){
        val landmarkList = mutableListOf<PoseLandmarkDataclass>()
        for (item in data.allPoseLandmarks){
            val landmarkName = item.landmarkType
            val landmarkPoint = item.position
            val landmark2json_tmp = PoseLandmarkDataclass(landmarkId = landmarkName, landmarkX = landmarkPoint.x, landmarkY = landmarkPoint.y)
            landmarkList.add(landmark2json_tmp)
        }
        TmpUDPData.putLandmarkListData(landmarkList)
    }

    fun object2Json(){

    }

    suspend fun sendData(){
        val socket = DatagramSocket()
        while (true){
            delay(1000L/25)
            val packet = DatagramPacket()
        }
    }

//    fun sendPose(data: Pose){
//        val socket = DatagramSocket()
//        val buffer = data.toString().toByteArray()
//        for (item in data.allPoseLandmarks){
//            val landmarkName = item.landmarkType
//            val landmarkPoint = item.position
////            val sendData:String = "$landmarkPoint%$landmarkName";
//            val sendData: String = "$landmarkName"
//            val packet = DatagramPacket(sendData.toByteArray(), sendData.toByteArray().size, ip, port)
//            socket.send(packet)
//        }
//        socket.close()
//    }
}