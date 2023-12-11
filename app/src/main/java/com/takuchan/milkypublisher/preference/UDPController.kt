package com.takuchan.milkypublisher.preference

import com.google.mlkit.vision.pose.Pose
import java.net.InetAddress
import java.net.DatagramSocket
import java.net.DatagramPacket

class UDPController {
    var ip = InetAddress.getByAddress(byteArrayOf(192.toByte(), 168.toByte(), 1.toByte(), 255.toByte()))
    var port = 5000

    fun receive(): String{
        val socket = DatagramSocket(port)

        val buffer = ByteArray(8192)
        val packet = DatagramPacket(buffer, buffer.size)

        socket.receive(packet)
        socket.close()

        return String(buffer)
    }

    fun send(data: Pose){
        val socket = DatagramSocket()
        val buffer = data.toString().toByteArray()
        val packet = DatagramPacket(buffer, buffer.size, ip, port)

        socket.send(packet)
        socket.close()
    }
}