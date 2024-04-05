package com.takuchan.milkypublisher.compose

import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.takuchan.milkypublisher.R
import com.takuchan.milkypublisher.viewmodel.ControllerViewModel


//絶対必要
//緊急停止用ボタン
//バッテリー残量・電波強度
//ロボットが接続しているネットワーク状況
//PC接続済みのデバイス一覧
//PC接続しているカメラ画像
//
//ここで問題
//モニタリングツールなのか、操作ツールなのか
//https://www.irobot-jp.com/irobothomeapp.html　参考
//
//誰がどこでどのように使うのかを想定
//家庭用なのか産業用なのか
//
//
//https://www.youtube.com/watch?v=3zv3J3X1V5A
//TODO: 使用者の対象を想定する。年齢層や職種なども想定
//TODO: パソコン中級者の定義を決めておく。個人的な。


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun RobotControllerScreen(
    controllerViewModel: ControllerViewModel = hiltViewModel(),
    navHomeController: NavController,
    navMainController: NavController
){

    //Buttonを押している間の処理

    controllerViewModel.setUpButton(false)
    controllerViewModel.setDownButton(false)
    controllerViewModel.setLeftButton(false)
    controllerViewModel.setRightButton(false)


    Box(modifier = Modifier.fillMaxSize()){
        Scaffold(
        ) {padding ->
            Column(modifier = Modifier
                .padding(padding)
                .padding(12.dp)) {
                Text("接続先のノードを指定することで操作が可能です。"
                , style = MaterialTheme.typography.bodySmall
                )
                Text("Server: odom/geometry",
                    style = MaterialTheme.typography.bodyMedium)
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 12.dp)
                ) {
                    Column(){
                        //おしゃれにバッテリー表示とシグナル表示を行う
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        ImageVector.vectorResource(id = R.drawable.battery),
                                        contentDescription = "Battery"
                                    )
                                    Text(
                                        text = "Battery: 100%",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        ImageVector.vectorResource(id = R.drawable.signal),
                                        contentDescription = "Signal"
                                    )
                                    Text(
                                        text = "Signal: 100%",
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                onClick ={}
                            ){
                                Text("緊急停止")
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            IconButton(
                                onClick = { /*xTODO*/ },
                                modifier = Modifier.size(60.dp).pointerInteropFilter {
                                    when (it.action) {
                                        MotionEvent.ACTION_DOWN -> {
                                            controllerViewModel.setUpButton(true)
                                        Log.d("application","holgind")}
                                        MotionEvent.ACTION_UP -> {
                                            controllerViewModel.setUpButton(false)
                                        }
                                    }
                                    true
                                },
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Up"
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                IconButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier.size(60.dp).pointerInteropFilter {
                                        when (it.action) {
                                            MotionEvent.ACTION_DOWN -> {
                                                controllerViewModel.setLeftButton(true)
                                            }
                                            MotionEvent.ACTION_UP -> {
                                                controllerViewModel.setLeftButton(false)
                                            }
                                        }
                                        true
                                    },
                                ) {
                                    Icon(
                                        Icons.Default.KeyboardArrowLeft,
                                        contentDescription = "Left"
                                    )
                                }

                                IconButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier.size(60.dp).pointerInteropFilter {
                                        when (it.action) {
                                            MotionEvent.ACTION_DOWN -> {
                                                controllerViewModel.setRightButton(true)
                                            }
                                            MotionEvent.ACTION_UP -> {
                                                controllerViewModel.setRightButton(false)
                                            }
                                        }
                                        true
                                    },
                                ) {
                                    Icon(
                                        Icons.Default.KeyboardArrowRight,
                                        contentDescription = "Right"
                                    )
                                }
                            }

                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.size(60.dp).pointerInteropFilter {
                                    when (it.action) {
                                        MotionEvent.ACTION_DOWN -> {
                                            controllerViewModel.setDownButton(true)
                                        }
                                        MotionEvent.ACTION_UP -> {
                                            controllerViewModel.setDownButton(false)
                                        }
                                    }
                                    true
                                },
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Down"
                                )
                            }
                        }
                    }

                }
            }
            
        }
    }
}


@Preview
@Composable
fun RobotControllerScreenPreview(){
    RobotControllerScreen(navHomeController = rememberNavController(), navMainController = rememberNavController())
}