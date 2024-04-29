package com.takuchan.milkypublisher.viewmodel

import android.util.MutableFloat
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takuchan.milkypublisher.model.DetectStateEnum
import com.takuchan.milkypublisher.model.DetectTypeEnum
import com.takuchan.milkypublisher.model.LogData
import com.takuchan.milkypublisher.model.LogScreenData
import com.takuchan.milkypublisher.model.LogScreenEnum
import java.util.Date

class LogViewModel:ViewModel() {
    // FaceDetectionでのViewModel
    private val _smiling: MutableLiveData<MutableFloatState> = MutableLiveData(mutableFloatStateOf(0.0f))
    val smiling: MutableLiveData<MutableFloatState> = _smiling

    fun putSmiling(data:Float){
        _smiling.value?.floatValue = data
    }


    private val _logList: MutableLiveData<MutableList<LogData>> = MutableLiveData(mutableListOf(
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

    //LogScreen用のViewModel
    private val _logScreenList: MutableLiveData<MutableList<LogScreenData>> = MutableLiveData(mutableListOf(
        LogScreenData(
            date = Date(),
            title = LogScreenEnum.Application.name,
            subtitle = "MilkyPublisherを起動しました。"
        )
    ))
    val logScreenList: MutableLiveData<MutableList<LogScreenData>> = _logScreenList

    fun addLogScreenList(data: LogScreenData){
        if(logScreenList.value!!.size > 100){
            logScreenList.value?.removeFirst()
            _logScreenList.value?.add(data)
        }else{
            _logScreenList.value?.add(data)
        }
    }




}