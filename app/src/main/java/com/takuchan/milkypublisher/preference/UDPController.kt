package com.takuchan.milkypublisher.preference

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.google.mlkit.vision.pose.Pose
import com.takuchan.milkypublisher.model.PoseLandmarkSingleDataClass
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
import java.text.SimpleDateFormat


object TmpUDPData{
    lateinit var landmarkListData:MutableList<PoseLandmarkSingleDataClass>

    fun putLandmarkListData(data: MutableList<PoseLandmarkSingleDataClass>){
        landmarkListData = data
    }
}

class UDPController(
) {

    private val serverIp = "192.168.62.20"
    private var port = 4001
    private val sendInterval = 40L
    private val gson = Gson()
    private val socket = DatagramSocket()

    fun startSending(flag: Boolean){
        if (flag){
            GlobalScope.launch{
                while(true){
                    sendData()
                    delay(sendInterval)
                }
            }
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun sendData(){
        val currentTimeMillis = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
        val date = dateFormat.format(currentTimeMillis)
        val mainFrame = MainFrame(
            date = date,
            frameSetting = FrameSetting(
                frameResolutionHeight = 1080,
                frameResolutionWidth = 1920,
                color = "RGB"
            ),
            controller = Controller(
                velocity = 0.0f,
                arrow = "UP"
            ),
            detectData = DetectData(
                mutablePose = TmpUDPData.landmarkListData
            )
        )

        val jsonData = gson.toJson(mainFrame)
        Log.d("sample",jsonData)
        val bytes = jsonData.toByteArray(Charsets.UTF_8)

        val packet = DatagramPacket(bytes, bytes.size, InetAddress.getByName(serverIp), port)
        socket.send(packet)
    }


    private data class MainFrame(
        val date:String,
        val frameSetting: FrameSetting,
        val controller: Controller,
        val detectData: DetectData
    )
    private data class FrameSetting(
        val frameResolutionHeight: Int,
        val frameResolutionWidth: Int,
        val color: String,
    )

    private data class Controller(
        val velocity: Float,
        val arrow: String,
    )


    private data class DetectData(
        val mutablePose: MutableList<PoseLandmarkSingleDataClass>
    )




    var ip = InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(), 0.toByte(), 199.toByte()))
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

//    fun sendPose(data: Pose){
//        val landmarkList = mutableListOf<PoseLandmarkDataclass>()
//        for (item in data.allPoseLandmarks){
//            val landmarkName = item.landmarkType
//            val landmarkPoint = item.position
//            val landmark2json_tmp = PoseLandmarkDataclass(landmarkId = landmarkName, landmarkX = landmarkPoint.x, landmarkY = landmarkPoint.y)
//            landmarkList.add(landmark2json_tmp)
//        }
//        TmpUDPData.putLandmarkListData(landmarkList)
//    }



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