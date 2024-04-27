package com.takuchan.milkypublisher.preference

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
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

    var floatx: Float = 0.0f
    var floatY: Float = 0.0f

    var smiling: Float = 0.0f

    fun putFloatX(data: Float){
        floatx = data
    }
    fun putFloatY(data: Float){
        floatY = data
    }
    fun putLandmarkListData(data: MutableList<PoseLandmarkSingleDataClass>){
        landmarkListData = data
    }
    fun putSmiling(data: Float){
        Log.d("TMPUdpData",data.toString())
        smiling = data
    }
}

class UDPController(
) {

    private var serverIp = ""
    private var port = 4001
    private val sendInterval = 40L
    private val gson = Gson()


    fun setServerIp(ip: String){
        this.serverIp = ip
    }
    fun setPort(port: Int){
        this.port = port
    }
    private var socket: DatagramSocket? = null

    fun startSending(boolean: Boolean){
        socket = DatagramSocket()
        GlobalScope.launch{
            do{
                Log.d("UDPController", "Sending")
                sendData()
                delay(sendInterval)
                if(!boolean){
                    socket?.close()
                    break
                }
            }while(boolean)
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
                floatX = TmpUDPData.floatx,
                floatY = TmpUDPData.floatY
            ),
            detectData = DetectData(
                mutablePose = TmpUDPData.landmarkListData,
                mutableFace = FaceDetection(TmpUDPData.smiling)
            )
        )

        val jsonData = gson.toJson(mainFrame)
        val bytes = jsonData.toByteArray(Charsets.UTF_8)

        val packet = DatagramPacket(bytes, bytes.size, InetAddress.getByName(serverIp), port)
        socket?.send(packet)
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
        val floatX: Float,
        val floatY: Float,
    )
    private data class FaceDetection(
        val smiling: Float
    )

    private data class DetectData(
        val mutablePose: MutableList<PoseLandmarkSingleDataClass>,
        val mutableFace: FaceDetection

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