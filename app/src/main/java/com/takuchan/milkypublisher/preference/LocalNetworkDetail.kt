package com.takuchan.milkypublisher.preference

import android.bluetooth.BluetoothClass.Device
import java.net.NetworkInterface
import java.util.Enumeration

class LocalNetworkDetail {
    public fun getDeviceInNetowrk(): List<Device>{
        val devices = mutableListOf<Device>()
        val enumNetworkInterfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
        while (enumNetworkInterfaces.hasMoreElements()) {
            val networkInterface: NetworkInterface = enumNetworkInterfaces.nextElement()
            val enumInetAddress = networkInterface.inetAddresses
            while (enumInetAddress.hasMoreElements()) {
                val inetAddress = enumInetAddress.nextElement()
                if (inetAddress.isSiteLocalAddress) {
                    val device = Device(networkInterface.displayName, inetAddress.hostAddress)
                    devices.add(device)
                }
            }
        }
        return devices
    }
    data class Device(val name: String, val ip: String)

}