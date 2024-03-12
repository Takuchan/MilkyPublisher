package com.takuchan.milkypublisher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takuchan.milkypublisher.enums.ConnectingEnum

class ConnectingViewModel : ViewModel() {

    //Dialogの表示ステータスの設定を行う
    var _showWifiDialog: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var showWifiDialog: LiveData<Boolean> = _showWifiDialog
    var _showBluetoothDialog: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var showBlueToothDialog: LiveData<Boolean> = _showBluetoothDialog
    var _showDialog: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var showDialog: LiveData<Boolean> = _showDialog

    //接続先情報を保存する
    var _wifiIpAddr: MutableLiveData<String> = MutableLiveData<String>("")
    var _wifiPort: MutableLiveData<String> = MutableLiveData<String>("")
    var _bluetoothAddr: MutableLiveData<String> = MutableLiveData<String>("")

    //接続先情報を取得する
    var wifiIpAddr: LiveData<String> = _wifiIpAddr
    var wifiPort: LiveData<String> = _wifiPort
    var bluetoothAddr: LiveData<String> = _bluetoothAddr


    //接続ステータスを表示
    var _connectingStatus: MutableLiveData<String> = MutableLiveData(ConnectingEnum.None.name)
    var connectingStatus: LiveData<String> = _connectingStatus



    //setterを作る
    fun setWifiIpAddr(ipAddr: String) {
        _wifiIpAddr.value = ipAddr
    }

    fun setWifiPort(port: String) {
        _wifiPort.value = port
    }

    fun setBluetoothAddr(addr: String) {
        _bluetoothAddr.value = addr
    }

    fun setConnectingStatus(status: String) {
        _connectingStatus.value = status
    }

    fun setShowWifiDialog(status: Boolean) {
        _showWifiDialog.value = status
    }

    fun setShowBluetoothDialog(status: Boolean) {
        _showBluetoothDialog.value = status
    }

    fun setShowDialog(status: Boolean) {
        _showDialog.value = status
    }

}