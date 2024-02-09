package com.takuchan.milkypublisher.viewmodel

import androidx.compose.runtime.MutableFloatState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takuchan.milkypublisher.model.DetectStateEnum
import com.takuchan.milkypublisher.model.DetectTypeEnum
import com.takuchan.milkypublisher.model.LogData

class LogViewModel:ViewModel() {
    val _logList: MutableLiveData<MutableList<LogData>> = MutableLiveData(mutableListOf(
        LogData(
            detectType = DetectTypeEnum.ApplicationDetection,
            detectState = DetectStateEnum.Start,
            detectTime = java.util.Date(),
            detectData = "姿勢検出を開始しました"
    )))
    val logList: MutableLiveData<MutableList<LogData>> = _logList

    //100個ログがたまったら自動的に削除を行うプログラム
    fun addLogList(data: LogData){
        if(logList.value!!.size > 100){
            logList.value?.removeFirst()
            _logList.value?.add(data)
        }else{
            _logList.value?.add(data)
        }
    }


}