package com.takuchan.milkypublisher.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takuchan.milkypublisher.model.ControllerModel
import javax.inject.Inject

class ControllerViewModel constructor(

): ViewModel() {

    var _upButton: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var _downButton: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var _leftButton: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var _rightButton: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)


    var _controllerModel : MutableLiveData<ControllerModel> = MutableLiveData<ControllerModel>()
    fun setUpButton(data:Boolean){
        _upButton.value = data
        updateControllerModel()
    }
    fun setDownButton(data:Boolean){
        _downButton.value = data
        updateControllerModel()

    }
    fun setLeftButton(data:Boolean){
        _leftButton.value = data
        updateControllerModel()

    }
    fun setRightButton(data:Boolean){
        _rightButton.value = data
        updateControllerModel()

    }

    fun updateControllerModel(){
        _controllerModel.value = ControllerModel(
            velocity = 1.0f,
            upArrow = _upButton.value ?: false,
            downArrow = _downButton.value ?: false,
            leftArrow = _leftButton.value ?: false,
            rightArrow = _rightButton.value ?: false
        )
    }
}