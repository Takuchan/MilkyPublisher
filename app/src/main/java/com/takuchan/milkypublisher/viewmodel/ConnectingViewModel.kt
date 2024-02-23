package com.takuchan.milkypublisher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takuchan.milkypublisher.enum.ConnectingEnum

class ConnectingViewModel:ViewModel() {
    //接続先情報を保存する
    var _wifiIpAddr: MutableLiveData<String> = MutableLiveData<String>("")
    var _bluetoothAddr: MutableLiveData<String> = MutableLiveData<String>("")

    //接続先情報を取得する
    var wifiIpAddr: LiveData<String> = _wifiIpAddr
    var bluetoothAddr: LiveData<String> = _bluetoothAddr

    //接続ステータスを表示
    var _connectingStatus: MutableLiveData<ConnectingEnum> = MutableLiveData(ConnectingEnum.None)
    var connectingStatus: LiveData<ConnectingEnum> = _connectingStatus


}