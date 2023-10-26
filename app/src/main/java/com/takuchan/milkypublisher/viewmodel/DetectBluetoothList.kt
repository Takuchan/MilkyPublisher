package com.takuchan.milkypublisher.viewmodel

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
    private val _detectbluetoothList = MutableLiveData<ArrayList<BluetoothNowState>>()
    val bluetoothList: LiveData<ArrayList<BluetoothNowState>> = _detectbluetoothList

    fun addBluetoothList(data: BluetoothNowState){
        _detectbluetoothList.value?.add(data)
    }
}


