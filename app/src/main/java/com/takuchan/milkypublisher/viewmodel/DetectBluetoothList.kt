package com.takuchan.milkypublisher.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takuchan.milkypublisher.model.BluetoothNowState

class DetectBluetoothList: ViewModel(){
    /*
    収集するもの
    ①BluetoothのMACアドレス
    ②Bluetoothの名前
     */
    private val _detectbluetoothList = MutableLiveData<MutableList<BluetoothNowState>>(
        mutableStateListOf()
    )
    val bluetoothList: LiveData<MutableList<BluetoothNowState>> = _detectbluetoothList

    fun addBluetoothList(data: BluetoothNowState){
        _detectbluetoothList.value?.add(data)
    }
}


