package com.takuchan.milkypublisher.compose

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController



絶対必要
緊急停止用ボタン
バッテリー残量・電波強度
ロボットが接続しているネットワーク状況
PC接続済みのデバイス一覧
PC接続しているカメラ画像

ここで問題
モニタリングツールなのか、操作ツールなのか
https://www.irobot-jp.com/irobothomeapp.html　参考

誰がどこでどのように使うのかを想定
家庭用なのか産業用なのか

//TODO: 使用者の対象を想定する。年齢層や職種なども想定
//TODO: パソコン中級者の定義を決めておく。個人的な。



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobotControllerScreen(
    navHomeController: NavController,
    navMainController: NavController){
    Box(modifier = Modifier.fillMaxSize()){
        Scaffold(
        ) {padding ->
            Column(modifier = Modifier
                .padding(padding)
                .padding(12.dp)) {
                Text("接続先のノードを指定することで操作が可能です。")
                Text("Server: odom/geometry")
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.size(60.dp)
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
                                modifier = Modifier.size(60.dp)
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowLeft,
                                    contentDescription = "Left"
                                )
                            }

                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.size(60.dp)
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Right"
                                )
                            }
                        }

                        IconButton(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.size(60.dp)
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

